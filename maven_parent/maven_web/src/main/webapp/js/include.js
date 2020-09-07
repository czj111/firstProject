$(function () {
    $.get("header.html",function (data) {
        $("#header").html(data);
    });
    $.get("footer.html",function (data) {
        $("#footer").html(data);
    });
    acceptContent();

});
function acceptContent(){
    if(window.location.hash=="" || window.location.hash==null)
    {
        location.href="homePage.html#1";
    }
    var value=window.location.hash;
    // alert(value);
    $.get("function.html"+value,function(data){
        $("#content").html(data);
    });
}