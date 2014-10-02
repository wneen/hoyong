var Thing=require( './db' );
var mqtt= require('mqtt')
  , client = mqtt.createClient();
var async = require('async');

var total=[];

client.subscribe('+/db/#');

client.on('message', function(topic, message) {
    console.log("TOPIC : "+topic + "     MESSAGE :" + message);
     
    var token=topic.split('/');
    removetoken(token,'db');

    async.series([
    function(callback) {  
    for(var i=0;i<token.length ;i++){

        if(token[i]=='put'){ 
        token.splice(i,1);
        var retopic =token.join('/'); //data sum
//        console.log('sum ='+ retopic);
        var nowdate = new Date();
        Thing.create({key : retopic, value : message, date : nowdate},function(err){  //data save
//        console.log('-TOPIC :'+topic);
//        console.log('-MESSAGE : '+message);
//        console.log('-Time : '+nowdate);
//        console.log('');
        }
        );}
       else if(token[i]=='get'){
        token.splice(i,1);
        var keyword= token.join('/');
        console.log('KEYWORD :' +keyword);

        Thing.find({key:keyword}).sort({date: -1}).exec(function(err,docs){

//         console.log(Object.keys(docs).length);//find length
         var limit=parseInt(message);
         var count=0;
         docs.forEach(function(doc){   
         if(count++<limit){           //message count
           var arr={};
           arr ['value']=doc.value;   // data save
           arr ['date']=doc.date;     
           total.push(arr);            //array data save 
           }
         });
         var jsonObj={dataList:total};
         console.log(jsonObj);
         var jsonObject=JSON.stringify(jsonObj);  //string to json
//         console.log(jsonObject);
           client.publish(keyword,jsonObject,function(err){total=[];});
         });
       }
    }   
      callback(null);
    }  
    ]
    );
});

function removetoken(array,a){
 for(var i=0;i<array.length ;i++){
  if(array[i]==a){
   array.splice(i,1);
  }
 }
return array;
}





client.options.reconnectPeriod = 0;  // disable automatic reconnec1t
