
var socket
function sendRequest(event, requestObj, focusSubject, callback) {

	if (socket == null) {
		socket = io.connect('http://localhost:10015');
	}
	// TODO 判断用户是否已经登录 ， data.uid

	// 连接上server后
	socket.on('connect', function() {
		// 发送握手请求
		this.emit(event, requestObj);

		this.on(focusSubject, function(data, ackServerCallback, arg1) {

			// res = $.base64(data)

			// alert(data + arg1)

			callback(data)
			// alert( $.base64.atob(data))
			// base64转码的数据，可忽视
			// YUNM.session = {
			// sessionId : $.base64
			// .atob(data.sessionId),
			// time : $.base64
			// .atob(data.time)
			// };
			if (ackServerCallback) {
				ackServerCallback('server message was delivered to client!');
			}
		});
	});

	// 如果web端session超时，socket断开，10分钟扫描一次
	int = window.setInterval(function() {
		// 通过ajax判断session超时的，你也可以通过其他方式
		$.ajax({
			type : 'POST',
			url : common.ctx + "/getSessionTimeout",
			dataType : "json",
			cache : false,
			success : function(json) {
				var timeout = parseInt(json.message);
				// session超时后，socket断开，服务端就可以监听到释放资源
				if (timeout == 0) {
					socket.disconnect();
				}
			},
			error : function() {
				socket.disconnect();
				// 清除
				window.clearInterval(int);
			}

		});
	}, 1000 * 60 * 10);

}

var ListenerRegister = {
		socketHost : "http://localhost:10015",
		socketServer : null,
		register : function(listener) {
			
			// TODO 判断用户是否已经登录 ， data.uid
						if (this.socketServer == null) {
							this.socketServer = io.connect(this.socketHost);
							// // 连接上server后
						}
						this.socketServer.on('connect',function() {
							// // 发送握手请求
							this.emit(listener.event, listener.requestObj);
							//
							this.on(listener.focusSubject,
								  function(data, ackServerCallback,arg1) {
											// res = $.base64(data)
											listener.callback(data)

											// TODO 保存 session
											if (ackServerCallback) {
												ackServerCallback('server message was delivered to client!');
											}
						          });
						    });
					}
	}

// var taskRefresher={
// btn:$('#taskDesc'),
// init:function(){
// var that=this;
// alert(this);
// this.btn.click(function(){
// that.change();
// alert(this);
// })

// },
// change:function(){
// this.btn.css({'background':'green'});

// }
// }

// var Circle = function() {
// var obj = new Object();
// obj.PI = 3.14159;

// obj.area = function( r ) {
// return this.PI * r * r;
// }
// return obj;
// }

// var c = new Circle();
// alert( c.area( 1.0 ) );
