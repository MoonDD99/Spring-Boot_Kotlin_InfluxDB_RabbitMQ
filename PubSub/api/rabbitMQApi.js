const Pub = require("../rabbitmq/pub");
const Sub = require("../rabbitmq/sub");
var rabbitMQConfig = require("../config/rabbitMQConfig.json")

module.exports = {
    publish : async (id, status) => {
        try{
            const connection = new Pub();
            const exchangName = rabbitMQConfig.topic + `${id}`;
            const message = {
                "id": id ,
                "status": status
            };
    
            var result = await connection.publishToExchange(exchangName, message);
    
            return result;
        }catch (error){
            console.log(error);
        }
    },

    consume : async (queName, exchangeName) => {
        try{
            const connection = new Sub();
            await connection.bindQueExchange(queName,exchangeName);
            await connection.consumeExchange(queName);
        }catch(error){
            console.log(error);
        }
    }
}
