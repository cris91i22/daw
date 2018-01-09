$(document).ready(function() {
    $('.btn-pedir').click(function () {
        $('#order').css("display", "block");
        var valu = $(this).data('id');
        var element = $("<input id=\"bolsonId\" type=\"text\" name=\"bolsonId\">");
        element.val(valu);
        element.css("display", "none");
        $('#contact').append(element);
    })
    $('#see-more').click(function () {
        $.get( "/moreQuestions", function(data) {
            var lis = '';

            for( var i = 0, l = data.length; i < l; i++ ) {
                var pregunta = data[i];
                var preguntaHtml =
                    '<div class="panel-heading" data-id="@pregunta.id">' +
                    '<h4 class="panel-title">' +
                    '<a data-toggle="collapse" href="#collapse' + ($('.panel-title').length + i) + '"># ' + pregunta.description + '</a>' +
                    '<p class="owner"> <b>Creador: </b>' + pregunta.name + '<b>Fecha: </b>' + pregunta.date + '</p>' +
                    '</h4>' +
                    '</div>' +
                    '<div id="collapse' + ($('.panel-title').length + i) + '" class="panel-collapse collapse">' +
                    '<ul class="list-group">';
                var resp = '';
                for ( var j = 0, t = data[i].answers.length; j < t; j++ ) {
                    var respuesta = pregunta.answers[j];
                    var respuestaHtml = '<li class="list-group-item"> - ' + respuesta.description +  '<p class="owner"> <b>Creador: </b>' +
                        respuesta.user + '<b>Fecha: </b>' + respuesta.date + '</p></li>';

                    resp += respuestaHtml;
                }
                preguntaHtml += resp +
                    '</ul>' +
                    '</div>';
                lis += preguntaHtml;
            }
            $("#see-more").before(lis);
        })
        .fail(function() {
            alert( "error" );
        });
    });

    var modal = $('#login');

    $('#loginbtn').click(function () {
        modal.css("display", "block");
    });

    modal.click(function () {
        if (event.target == modal) {
            modal.css("display", "none");
        }
    });

    $('.close').click(function () {
        modal.css("display", "none")
    });

    $('.cancelbtn').click(function () {
        modal.css("display", "none")
    })
});




