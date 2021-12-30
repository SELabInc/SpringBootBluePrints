let csrfToken = $("meta[name='_csrf']").attr("content");
let csrfHeader = $("meta[name='_csrf_header']").attr("content");

jQuery.ajaxSettings.traditional = true;

$.ajaxSetup({
    beforeSend: function(xhr){
        xhr.setRequestHeader("ajax", "true");
        xhr.setRequestHeader(csrfHeader, csrfToken);
    },
    error : function(xhr, status, errorThrown){

        switch (xhr.status) {
            case 403:
                window.location = '/signIn';
                break;
            default :
               // TODO default error handler

        }
    }
})