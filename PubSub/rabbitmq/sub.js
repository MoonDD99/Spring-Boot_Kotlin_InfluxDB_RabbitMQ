const amqp = require("amqplib");
var connectConfig = require("../config/connectConfig.json");

class Sub {
    constructor(){
        //객체 초기화
        this._url = connectConfig.url;

        this.channel = undefined;
    }

    //커넥트 생성하고 채널 연결
    async setUp(){
        const connect = await amqp.connect(this._url);
        const channel = await connect.createChannel();
        this.channel = channel;
    }

    async bindQueExchange(queueName,exchangeName){
        await this.setUp();
        this.channel.assertQueue(queueName);
        console.log(" [*] Waiting for messages in %s. To exit press CTRL+C",queueName);

        this.channel.bindQueue(queueName, exchangeName, '');
    }

    async consumeExchange(queueName){
        await this.setUp();
        this.channel.consume(queueName, (msg) => {
            if(msg != null){
                const jsonData = JSON.parse(msg.content);
                this.channel.ack(msg);
                console.log("id : " + jsonData['id'] + " status : " + jsonData['status']);

            }else {
                console.log(" [x] msg is null ");

            }
        });
    }

    }

module.exports = Sub;