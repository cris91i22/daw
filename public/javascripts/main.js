$(document).ready(function() {
    $('.btn-pedir').click(function () {
        $('#order').css("display", "block");
        var valu = $(this).data('id');
        var element = $("<input id=\"bolsonId\" type=\"number\" name=\"bolsonId\">");
        element.val(valu);
        element.css("display", "none");
        $('#contact').append(element);
    })
});