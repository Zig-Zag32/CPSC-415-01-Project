
import express from 'express';
import swaggerJsdoc from 'swagger-jsdoc';
import swaggerUi from 'swagger-ui-express';
import { Ollama } from 'ollama'; 

const app = express();
const port = 2001;

//change the host to the address of Ollama in K8s
const ollama = new Ollama({
    //host: 'http://ollama.default.svc.cluster.local:11434'ollama is not headless so have to go with its IP
    host: 'http://10.101.165.41:11434'
  });

const options = {
    swaggerDefinition: {
        openapi: '3.0.0',
        info: {
            title: 'Trincoll Bot',
            version: '1.0.0',
            description: 'Try to interact with large language models: 1',
        }
    },
    apis: ['./app.js'],
}

const specs = swaggerJsdoc(options)
app.use('/swagger', swaggerUi.serve, swaggerUi.setup(specs))
app.use(express.json())


// ---------------------------------------- Kitchen assistant Ai
/**
 * @swagger
 * /KitchenAssistant:
 *   post:
 *     summary: Continuous dialogue with LLM
 *     tags: [415-1 Group Project]
 *     description: API for getting recipe recommendations based on provided ingredients.
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
app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})

