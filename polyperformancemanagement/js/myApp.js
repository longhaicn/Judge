/**
 * Created by xiaomin on 16/10/22.
 */
var myApp = (function () {
    //扩展Date的format方法
    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S": this.getMilliseconds()
        };
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    };
    $.fn.extend({
        //表单加载json对象数据
        setForm: function (jsonValue) {
            var obj = this;
            $.each(jsonValue, function (name, ival) {
                var $oinput = obj.find("input[name=" + name + "]");
                if ($oinput.attr("type") == "checkbox") {
                    if (ival !== null) {
                        var checkboxObj = $("[name=" + name + "]");
                        var checkArray = ival.split(";");
                        for (var i = 0; i < checkboxObj.length; i++) {
                            for (var j = 0; j < checkArray.length; j++) {
                                if (checkboxObj[i].value == checkArray[j]) {
                                    checkboxObj[i].click();
                                }
                            }
                        }
                    }
                }
                else if ($oinput.attr("type") == "radio") {
                    $oinput.each(function () {
                        var radioObj = $("[name=" + name + "]");
                        for (var i = 0; i < radioObj.length; i++) {
                            if (radioObj[i].value == ival) {
                                radioObj[i].click();
                            }
                        }
                    });
                }
                else if ($oinput.attr("type") == "textarea") {
                    obj.find("[name=" + name + "]").html(ival);
                }
                else {
                    obj.find("[name=" + name + "]").val(ival);
                }
            })
        },
        serializeObject: function () {
            var serializeObj = {},
                array = this.serializeArray();
            $.each(array, function () {
                serializeObj[this.name] = this.value;
            });
            return serializeObj;
        }
    });
    return {
        serviceURL: "http://218.17.55.18:8909/",
        loginURL: "http://218.17.55.18:8909/polyperformancemanagement/index.html",
        baseURL: "http://218.17.55.18:8909/web-front/",
        imageURL: "http://218.17.55.18:8909/resources/image/",
        action: {
            CR: 'create', ER: 'edit', UNDO: 'undo', DR: 'del', LR: 'look'
        },
        userRole: ["", "最高负责人", "项目总负责人", "执行负责人", "组员"],
        projectClass: ["", "三级优先", "二级优先", "一级优先"],
        getUser: function () {
            var user = this.getCookie('$poly_admin');
            if (user) {
                this.setUser(user);
                return $.parseJSON(user);
            } else return null;
        },

        checkUser: function () {
            var user = this.getUser();
            if (user) {
                return user;
            } else {
                window.location.replace(this.loginURL);
            }
        },

        setUser: function (value) {
            this.setCookie('$poly_admin', value, 1);
        },

        setCookie: function (c_key, value, expires) {
            $.cookie(c_key, value, {expires: expires});
        },
        getCookie: function (c_key) {
            return $.cookie(c_key);
        },
        removeCookie: function () {
            $.cookie('$poly_admin', null);
        },
        Util: {
            getUrlParam: function (name, a_window) {
                a_window = a_window || window;
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
                var r = a_window.location.search.substr(1).match(reg); //匹配目标参数
                if (r != null) return unescape(r[2]);
                return null; //返回参数值
            },
            getAllData: function (a_cfg) {
                var callback = a_cfg.callback;
                a_cfg.callback = function (cpData) {
                    var data = cpData.data;
                    callback && callback(data, cpData.code, cpData.desc);
                };
                this.submitRequest(a_cfg);
            },
            getObj: function (a_cfg) {
                var callback = a_cfg.callback;
                a_cfg.callback = function (cpData) {
                    var data = cpData.data;
                    callback && callback(data && data[0], cpData.code, cpData.desc);
                };
                this.submitRequest(a_cfg);
            },
            datetimeFormat: function (datetime, format) {
                var reg = /^(1)[0-9]{12}$/;
				if(typeof datetime === "string" || typeof datetime === "number"){
					if (reg.test(datetime)) {
						var date = new Date(datetime);
						return date.format(format || "yyyy-MM-dd hh:mm:ss");
				}
			}else if(datetime.constructor === Date){
            return datetime.format(format || "yyyy-MM-dd hh:mm:ss");
			}
        return datetime;
            },
            submitRequest: function (a_cfg) {
                var callback = a_cfg.callback;
                delete a_cfg.callback;
                a_cfg.type || (a_cfg.type = 'POST');
                a_cfg.success = function (data, status) {
                    var result = data;
                    if (a_cfg.parseFn) {
                        result = a_cfg.parseFn(data);
                        callback(result);
                    } else if (a_cfg.parseFn !== false) {
                        callback(result.responseText);
                    } else {
                        callback(result);
                    }
                };
                a_cfg.error = function (data, status) {
                    alert("服務端錯誤信息:" + data.statusText + "联系王工");
                };
                $.ajax(a_cfg);
            },
            getScript: function (as_jsUrl) {
                var result = '';
                $.ajax({
                    url: as_jsUrl,
                    async: false,
                    type: 'GET',
                    success: function (data) {
                        result = data;
                    }
                });
                return result;
            },
            parseFn: function (a_data) {
                var code, data, desc;
                if (a_data) {
                    if (typeof a_data === 'string') {
                        a_data = $.parseJSON(a_data);
                    }
                    a_data['data'] && (data = a_data['data']);
                    code = a_data['code'];
                    desc = a_data['desc'];
                    if (code === '302') {
                        window.location.replace(myApp.loginURL);
                        return;
                    }
                }
                return {data: data, code: code, desc: desc}
            },
            basicAlert: function (text) {
                swal(text);
            },
            basicMessage: function (title, text, type) {
                swal({
                    title: title,
                    text: text,
                    type: type || 'info',
                    timer: 3000
                });
            },
            animateMessage: function (title, text, type, customClass) {
                swal({
                    title: title,
                    text: text,
                    type: type || 'info',
                    animation: false,
                    customClass: customClass || 'animated tada',
                    timer: 3000
                });
            }
        }
    };
})();
