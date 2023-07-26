let userObject = {
    init: function () {
        let _this = this;

        $("#btn-save").on("click", () => {
            _this.insertUser();
        });
    },

    insertUser: function () {
        alert("회원가입 요청됨");
        let user = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }
        $.ajax({
            type: "POST",
            url: "/auth/insertUser",
            data: JSON.stringify(user),
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
                if (errors.username != null) warn += errors.username + "\n";
                if (errors.password != null) warn += errors.password + "\n";
                if (errors.email != null) warn += errors.email;
                alert(warn);
            }
        }).fail(function (error) {
            alert("에러 발생 : " + error)
        });
    },
}

userObject.init();