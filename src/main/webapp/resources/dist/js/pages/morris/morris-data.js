// Dashboard 1 Morris-chart
function stat_filmset(barchart) {
    "use strict";
    const data=[]
    if(barchart!=null && barchart!=undefined){
        Array.from(barchart).forEach((item)=>{
            data.push({y:item[0],title:item[1]})
        })
    }
    
// Morris bar chart
    Morris.Bar({
        element: 'morris-bar-chart',
        data: datas,
        xkey: 'y',
        ykeys: ['title'],
        labels: ['plateau'],
        barColors:['#01caf1', '#5f76e8'],
        hideHover: 'auto',
        gridLineColor: '#eef0f2',
        resize: true
    });
 }  
