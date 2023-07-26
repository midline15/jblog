let postObject = {
    init: function () {

        $("#btn-insert").on("click", () => {
            this.insertPost();
        });
        $("#btn-update").on("click", () => {
            this.updatePost();
        });
        $("#btn-delete").on("click", () => {
            this.deletePost();
        });
    },

    insertPost: function () {
        alert("포스트 등록 요청됨");
        let post = {
            title: $("#title").val(),
            content: $("#content").val()
        }

        $.ajax({
            type: "POST",
            url: "/post",
            data: JSON.stringify(post),
            contentType: "application/json; charset=utf-8",
        }).done(function (response) {
            let status = response["status"];
            if (status == 200) {
                let message = response["data"];
                alert(message);
                location = "/";
            } else {
                let warn = "";
                let errors = response["data"];
                if (errors.title != null) warn += errors.title + "\n";
                if (errors.content != null) warn += errors.content;
                alert(warn);
            }
        }).fail(function (error) {
            let message = error["data"];
            alert("에러 발생 : " + message);
        });
    },

    updatePost: function () {
        alert("포스트 수정 요청됨");
        let post = {
            id : $("#id").val(),
            title: $("#title").val(),
            content: $("#content").val()
        }

        $.ajax({
            type: "PUT",
            url: "/post",
            data: JSON.stringify(post),
            contentType: "application/json; charset=utf-8",
        }).done(function (response) {
            let message = response["data"];
            alert(message);
            location = "/";
        }).fail(function (error) {
            let message = error["data"];
            alert("에러 발생 : " + message);
        });
    },

    deletePost: function () {
        alert("포스트 삭제 요청됨");
        let id = $("#id").val();

        $.ajax({
            type: "DELETE",
            url: "/post/"+ id,
            contentType: "application/json; charset=utf-8",
        }).done(function (response) {
            let message = response["data"];
            alert(message);
            location = "/";
        }).fail(function (error) {
            let message = error["data"];
            alert("에러 발생 : " + message);
        });
    },
}

postObject.init();