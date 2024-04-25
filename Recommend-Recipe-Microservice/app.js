
import express from 'express';
import swaggerJsdoc from 'swagger-jsdoc';
import swaggerUi from 'swagger-ui-express';
import { Ollama } from 'ollama'; 

import axios from 'axios';


const app = express();
const port = 2001;

//change the host to the address of Ollama in K8s
const ollama = new Ollama({
    host: 'http://ollama.default.svc.cluster.local:11434' //ollama is not headless so have to go with its IP
    // host: 'http://10.101.165.41:11434'
  });

const options = {
    swaggerDefinition: {
        openapi: '3.0.0',
        info: {
            title: 'Recommend-Recipe-Microservice',
            version: '1.0.0',
            description: 'This Microservice gets the stock of the user from Microservice 1 and sends it to LLM llama 2 with a prompt, getting a recipe recommend back which only uses stuff in the stock.',
        }
    },
    apis: ['./app.js'],
}

const specs = swaggerJsdoc(options)
app.use('/swagger', swaggerUi.serve, swaggerUi.setup(specs));
app.use(express.json());


//----------------------

/**
 * @swagger
 * /KitchenAssistant1:
 *   post:
 *     summary: Retrieve a recipe based on kitchen inventory
 *     description: This endpoint retrieves kitchen inventory items from Microservice 1 and uses them to request a recipe recommendation from the Ollama language model.
 *     tags: [Actual API]
 *     responses:
 *       200:
 *         description: Successfully retrieved a recipe recommendation based on the current kitchen inventory.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 answer:
 *                   type: string
 *                   description: Recipe recommendation based on the kitchen inventory data retrieved.
 *                   example: "Recommended dish: Fried Eggs; Time required: 5 minutes; Ingredients needed: two eggs."
 *       400:
 *         description: Bad request, for example, if the kitchen inventory data is incomplete or missing.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 error:
 *                   type: string
 *                   description: Error message indicating the request could not be processed due to missing or incomplete data.
 *                   example: "Invalid data from kitchen inventory service"
 *       500:
 *         description: Internal server error, typically when there is a failure in interacting with the Ollama language model or the microservice.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 error:
 *                   type: string
 *                   description: Error message indicating there was an issue processing the request.
 *                   example: "Failed to interact with LLM or retrieve data from microservice"
 */

app.post('/KitchenAssistant1', async (req, res) => {
    const ms1Url = 'http://kitchen-inventory-ms-service.krx-kitchen-inventory-ms.svc.cluster.local:8080/kitchenItems';
    try {
        const ms1Response = await axios.get(ms1Url);
        const kitchenItems = JSON.stringify(ms1Response.data); // 将获取的数据转换成字符串


      
        const response = await ollama.chat({
            model: 'llama2', 
            messages: [
                { 
                    role: 'system', 
                    content: 'Role: kitchen assistant AI. Traits: Recommend recipe that can be cooked according to the ingredients the user has. Scenario: Recommend recipe on an online forum.' 
                },
                {
                    role: 'user', 
                    content: kitchenItems // 将食材描述发送到OLLAMA
                }
            ],
        });
        res.send({answer: response.message.content});
    } catch (error) {
        console.error('Error during network or LLM interaction:', error);
        res.status(500).send({error: 'Failed to interact with LLM or fetch data'});
    }
});

//----------------------

// ---------------------------------------- Kitchen assistant Ai
/**
 * @swagger
 * /KitchenAssistant:
 *   post:
 *     summary: single dialogue with LLM
 *     tags: [Test]
 *     description: API for getting recipe recommendations based on provided ingredients by input.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - question
 *             properties:
 *               question:
 *                 type: string
 *                 example: 'I have two eggs'
 *                 description: Ingredients info.
 *     responses:
 *       200:
 *         description: Successfully received an answer from the large language model.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 answer:
 *                   type: string
 *                   example: 'I would recommend XXX... '
 *                   description: The recipe recommendations generated by LLM
 *       400:
 *         description: Bad request, such as when the required "question" field is missing.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 error:
 *                   type: string
 *                   example: 'Question is required'
 *                   description: Error message indicating the nature of the request error.
 *       500:
 *         description: Internal server error, for example, when there is a failure in interacting with the large language model.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 error:
 *                   type: string
 *                   example: 'Failed to interact with LLM'
 *                   description: Error message indicating there was an issue processing the request.
 */
app.post('/KitchenAssistant', async (req, res) => {
    const question = req.body.question; // This line was missing, which is necessary to read the question from the request body

    if (!question) {
        return res.status(400).send({error: 'Question is required'});
    }

    try {
        const response = await ollama.chat({
            model: 'llama2', 
            messages: [
                { 
                    role: 'system', 
                    content: 'Role: kitchen assistant AI. Traits: Recommend recipe that can be cooked according to the ingredients the user has. Scenario: Recommend recipe on an online forum. Example dialogue: User: I have two eggs. kitchen assistant AI: Recommended dish: Fried Eggs; Time required: 5 minus; Ingredients: one or more eggs. Directions: Step 1: In a small nonstick over medium heat, melt butter (or heat oil). Crack egg into pan. Cook 3 minutes, or until white is set. Step 2: Flip and cook 2 to 3 minutes more, until yolk is completely set' 
                },
                {
                    role: 'user', 
                    content: question // Fixed the typo here
                }
            ],
        });
        res.send({answer: response.message.content});
    } catch (error) {
        console.error('Error during LLM interaction:', error);
        res.status(500).send({error: 'Failed to interact with LLM'});
    }
});


// ----------------------------------------
/**
 * @swagger
 * /MS1Response:
 *   post:
 *     summary: Get kitchen items from MS1 service
 *     description: Retrieve kitchen items from MS1 service and send the data as JSON response
 *     responses:
 *       '200':
 *         description: Successful operation
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   name:
 *                     type: string
 *                     description: The name of the kitchen item
 *                   quantity:
 *                     type: number
 *                     description: The quantity of the kitchen item
 *                   unit:
 *                     type: string
 *                     description: The unit of measurement of the kitchen item
 *       '500':
 *         description: Failed to interact with MS1
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 error:
 *                   type: string
 *                   example: "Failed to interact with MS1"
 */

app.post('/MS1Response', async (req, res) => {
    const ms1Url = 'http://kitchen-inventory-ms-service.krx-kitchen-inventory-ms.svc.cluster.local:8080/kitchenItems';
    try {
        const ms1Response = await axios.get(ms1Url);
        res.send(ms1Response.data); // 直接将获取的数据作为响应发送给客户端
    } catch (error) {
        console.error('Error', error);
        res.status(500).send({error: 'Failed to interact with MS1'});
    }
});


//--------------
app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})

