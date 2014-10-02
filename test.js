var  good ='gggg/ddddd/eeee/ssss/aaaaa'

var token =good.split('/');
var token1=good.split('/');
//for(var i=0;i<token.length ;i++){
//console.log(token[i]);
//}

var kkk = restring('ddddd',token);
//console.log(kkk.length);
for(var i=0;i<kkk.length ;i++){
console.log(kkk[i]);
}


function restring(a,ggg){
//console.log(ggg.length);
for(var i=0;i<ggg.length ;i++){
if(ggg[i]==a){
ggg.splice(i,1);
console.log(ggg.length);
}
}

return ggg;

}
