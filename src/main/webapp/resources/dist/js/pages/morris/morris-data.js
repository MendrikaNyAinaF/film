// Dashboard 1 Morris-chart
$(function () {
    "use strict";

// Morris bar chart
    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            y: '2006',
            a: 100
        }, {
            y: '2007',
            a: 75
        }, {
            y: '2008',
            a: 50
        }, {
            y: '2009',
            a: 75
        }, {
            y: '2010',
            a: 50
        }, {
            y: '2011',
            a: 75
        }, {
            y: '2012',
            a: 100
        }],
        xkey: 'y',
        ykeys: ['a'],
        labels: ['A'],
        barColors:['#01caf1', '#5f76e8'],
        hideHover: 'auto',
        gridLineColor: '#eef0f2',
        resize: true
    });
 });  
