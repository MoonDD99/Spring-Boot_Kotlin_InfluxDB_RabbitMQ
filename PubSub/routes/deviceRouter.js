var express = require('express');
var router = express.Router();

const { publish,consume } = require("../api/rabbitMQApi");

router.post('/publish', async (req, res) => {
    try {
        var {id, status} = req.body;
        const response = publish(id, status);

        res.status(201).json({command : response})
    }catch(error){
        console.log(error);
    }
})

router.post('/consume', async (req, res) => {
    try{
        var {queName, exchangeName} = req.body;
        consume(queName, exchangeName);
        res.status(201).json({result : true});
    }catch(error){
        console.log(error);
    }
})

module.exports = router;