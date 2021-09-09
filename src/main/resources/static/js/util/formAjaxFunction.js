function formAjax($form, success) {
    let form = $form.get(0);

    if(!form.reportValidity()){
        return;
    }

    let formData = new FormData(form);
    $.ajax({
        url: $form.attr("action"),
        method: $form.attr("method"),
        data: formData,
        processData: false,
        contentType: false,
        success: success
    });
}