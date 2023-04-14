const amqp = require("amqplib");

class Pub {
    constructor(){
        //객체 초기화
        this._url = "amqp://localhost:5672"
        
        this.channel = undefined;
    }

    //커넥트 생성하고 채널 연결
    async setUp(){
        const connect = await amqp.connect(this._url);
        const channel = await connect.createChannel();
        this.channel = channel;
        //console.log(connect);
    }

    //exchange에 msg를 publish
    async publishToExchange(exchangeName, msg){
        await this.setUp();
        let success = this.channel.publish(exchangeName, '', Buffer.from(JSON.stringify(msg)));
        if (success){
            console.log(" [x] Success Sent %s", msg);
        }else {
            console.log(" [x] Fail Sent %s", msg);
        }
        return success;
    }
}

module.exports = Pub;
