// (1) 회원정보 수정
function update(userId, event) {
    event.preventDefault(); //폼태그 액션을 막는다.

    console.log("아이디: ",userId);
    let data = $("#profileUpdate").serialize();

    $.ajax({
        type:"put",
        url:`/api/user/${userId}`,
        data:data,
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        dataType:"json"
    }).done(res=>{ // httpStatus 상태코드가 200일 때
        console.log("성공", res);
        location.href=`/user/${userId}`
    }).fail(error=>{ // httpStatus 상태코드가 200이 아닐 때
        if(error.data == null){
            alert(error.responseJSON.message);
        }else{
            alert(JSON.stringify(error.responseJSON.data));
        }
    });
}