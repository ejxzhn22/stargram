

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribeHeader(toUserId,obj) {
	if ($(obj).text() === "구독취소") {

		$.ajax({
			type:"delete",
			url:"/api/subscribe/"+toUserId,
			dataType:"json"
		}).done(res=>{
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error=>{
			console.log("구독취소실패", error);
		});

	} else {

		$.ajax({
			type:"post",
			url:"/api/subscribe/"+toUserId,
			dataType:"json"
		}).done(res=>{
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error=>{
			console.log("구독하기실패", error);
		});

	}
}

// (2) 구독자 정보  모달 보기
function headerSubscribeInfoModalOpen() {
	$(".modal-subscribe-header").css("display", "flex");
	let keyword = $(".search-input").val();
	$.ajax({
		url:`/api/user/search/${keyword}`,
		dataType:"json"
	}).done(res=>{
		console.log("성공", res.data);
		res.data.forEach((u)=>{
			console.log("u.user : ", u.user);
			console.log("u : ", u);
			let item = getHeaderSubscribeModalItem(u.user);
			$("#subscribeHeaderModalList").append(item);
			let item2 = getHeaderSubscribeModalItem2(u);
			$("#subscribe__btn").append(item2);
		});
	}).fail(error=>{
		console.log("실패", error);
	})
}

function getHeaderSubscribeModalItem(u) {
	let item = `<div class="subscribe-header__item" id="subscribeHeaderModalItem-${u.id}">
	<div class="subscribe__img">
		<img src="/upload/${u.profileImageUrl}" onerror="this.src='/images/person.jpeg'"/>
	</div>
	<div class="subscribe__text">
		<h2 th:text="${u.username}">${u.username}</h2>
	</div>
	<div id="subscribe__btn" class="subscribe__btn">`;

	// if(!u.equalUserState){ // 동일 유저가 아닐 때 버튼이 만들어 져야함
	// 	if(u.subscribeState){ // 구독 한 상태
	// 		item += `<button class="cta blue" onclick="toggleSubscribeHeader(${u.id},this)">구독취소</button>`;
	//
	// 	}else{ // 구독 안 한 상태
	// 		item += `<button class="cta" onclick="toggleSubscribeHeader(${u.id},this)">구독하기</button>`;
	//
	// 	}
	// }
item +=`	
</div>
</div>`;

	console.log(item);
	return item;
}

function getHeaderSubscribeModalItem2(u) {
	let item = ``;
	if(!u.equalUserState){ // 동일 유저가 아닐 때 버튼이 만들어 져야함
		if(u.subscribeState){ // 구독 한 상태
			item += `<button class="cta blue" onclick="toggleSubscribeHeader(${u.user.id},this)">구독취소</button>`;

		}else{ // 구독 안 한 상태
			item += `<button class="cta" onclick="toggleSubscribeHeader(${u.user.id},this)">구독하기</button>`;

		}
	}
	console.log("item2: ",item);
	return item;
}


// (7) 구독자 정보 모달 닫기
function modalHeaderClose() {
	$(".modal-subscribe-header").css("display", "none");
	location.reload();
}








