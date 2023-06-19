// function view_quantity(id){
//     var date = document.getElementById('dateContent').value;
//     $.ajax({
//         type:"GET",
//         url:"api/viewQuantity/"+ id + '/' + date,
//         success:function (data){
//             var content = '';
//             content +=`Số lượng còn: <span style="color: #9c3328">`+data.quantity+`</span>`
//             $('#quantityValue').html(content);
//         }
//     })
// }

function view_quantity(id){
    let date = document.getElementById('dateContent').value;
    $.ajax({
        url:"/api/viewQuantity/"+ id + '/' + date,
        type:"GET",
        success:function (data){
            let content = '';
            content +=`Số lượng còn: <span style="color: red" id="sumNumber">`+data.quantity+`</span>`
            content +=` <span style="display: none" id="sumNumber_copy" >`+data.quantity+`</span>`
            content +=`<input type="hidden" name="dateTourId" value="`+data.dateTourId+`" >`
            $('#quantityValue').html(content);
        }
    })
}