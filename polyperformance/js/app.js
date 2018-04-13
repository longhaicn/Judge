/**
 * 演示程序当前的 “注册/登录” 等操作，是基于 “本地存储” 完成的
 * 当您要参考这个演示程序进行相关 app 的开发时，
 * 请注意将相关方法调整成 “基于服务端Service” 的实现。
 **/
(function (mui, owner) {
    owner.serverUrl = "http://218.17.55.18:8909/";
    owner.polyURL = owner.serverUrl + "polyperformance/";
    owner.baseURL = owner.serverUrl+ "web-front/";
    owner.userRole = ["", "最高负责人", "项目总负责人", "执行负责人", "组员"];
    owner.projectClass = ["", "三级优先", "二级优先", "一级优先"];

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
                } else if ($oinput.attr("type") == "radio") {
                    $oinput.each(function () {
                        var radioObj = $("[name=" + name + "]");
                        for (var i = 0; i < radioObj.length; i++) {
                            if (radioObj[i].value == ival) {
                                radioObj[i].click();
                            }
                        }
                    });
                } else if ($oinput.attr("type") == "textarea") {
                    obj.find("[name=" + name + "]").html(ival);
                } else {
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
    /**
     * 用户登录
     **/
    owner.login = function (loginInfo, callback) {
        callback = callback || mui.noop;
        var pwd = loginInfo.password;
        if (pwd.length < 8) {
            return callback('密码至少8位字母和数字组合');
        }
        loginInfo.password = mui.md5(mui.sha1(pwd));
        owner.getObj({
            url: owner.baseURL + "account/login",
            data: {
                json: JSON.stringify(loginInfo)
            },
            parseFn: owner.parseFn,
            callback: function (a_record, a_code) {
                switch (a_code) {
                    case '0':
                        owner.setUser(JSON.stringify(a_record));
                        return callback();
                    case '208':
                        return callback("账号或密碼錯誤!");
                    default:
                        return callback("參數錯誤!");
                }
            }
        });
    };

    /**
     * 用户更改密码
     **/
    owner.changePassword = function (passwordInfo, callback) {
        callback = callback || mui.noop;
        var pwd = passwordInfo.password;
        if (pwd.length < 8 || passwordInfo.new_password.length < 8) {
            return callback('密码至少8位字母和数字组合');
        }
        if (passwordInfo.new_password != passwordInfo.new_password2) {
            return callback('新密码2次输入不一致');
        }
        passwordInfo.password = mui.md5(mui.sha1(pwd));
        passwordInfo.new_password = mui.md5(mui.sha1(passwordInfo.new_password));
        var user = owner.getUser();
        owner.getObj({
            url: owner.baseURL + "company/setpwd",
            data: {
                tag: owner.tag,
                code: user.code,
                token: user.token,
                json: JSON.stringify(passwordInfo)
            },
            parseFn: owner.parseFn,
            callback: function (a_record, a_code) {
                switch (a_code) {
                    case '210':
                        return callback();
                    case '211':
                        return callback("原密碼不对!");
                    case '205':
                    case '206':
                        return callback("帳號已凍結,請聯繫客服!");
                    default:
                        return callback("參數錯誤!");
                }
            }
        });
    };

    owner.checkAccount = function (account) {
        var reg = /^[a-z][0-9a-z]*$/;
        return reg.test(account);
    };

    owner.checkEmail = function (email) {
        email = email || '';
        return (email.length > 3 && email.indexOf('@') > -1);
    };

    owner.getUrlParam = function (name, a_window) {
        a_window = a_window || window;
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = decodeURI(a_window.location.search.substr(1)).match(reg); //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    };

    owner.parseParam = function(param, key) {
        var paramStr = "";
        if (param instanceof String || param instanceof Number || param instanceof Boolean) {
            paramStr += "&" + key + "=" + encodeURIComponent(param);
        } else {
            $.each(param, function(i) {
                var k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
                paramStr += '&' + owner.parseParam(this, k);
            });
        }
        return paramStr.substr(1);
    };

    owner.datetimeFormat = function (datetime, format) {
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
    };

    owner.getUser = function () {
        var user = this.getCookie('$polypm_account');
        if (user) {
            this.setUser(user);
            return $.parseJSON(user);
        } else return null;
    };

    owner.checkUser = function () {
        var user = this.getUser();
        if (user) {
            return user;
        } else {
            window.location.replace(this.polyURL + 'login.html');
        }
    };

    owner.setUser = function (value) {
        this.setCookie('$polypm_account', value, 1200);
    };

    owner.setCookie = function (c_key, value, expires) {
        $.AMUI.utils.cookie.set(c_key, value, expires);
    };
    owner.getCookie = function (c_key) {
        return $.AMUI.utils.cookie.get(c_key);
    };

    /**
     * 找回密码
     **/
    owner.forgetPassword = function (email, callback) {
        callback = callback || mui.noop;
        if (!checkEmail(email)) {
            return callback('邮箱地址不合法');
        }
        return callback(null, '新的随机密码已经发送到您的邮箱，请查收邮件。');
    };

    /**
     * 获取本地是否安装客户端
     **/
    owner.isInstalled = function (id) {
        if (id === 'qihoo' && mui.os.plus) {
            return true;
        }
        if (mui.os.android) {
            var main = plus.android.runtimeMainActivity();
            var packageManager = main.getPackageManager();
            var PackageManager = plus.android.importClass(packageManager);
            var packageName = {
                "qq": "com.tencent.mobileqq",
                "weixin": "com.tencent.mm",
                "sinaweibo": "com.sina.weibo"
            };
            try {
                return packageManager.getPackageInfo(packageName[id], PackageManager.GET_ACTIVITIES);
            } catch (e) {
            }
        } else {
            switch (id) {
                case "qq":
                    var TencentOAuth = plus.ios.import("TencentOAuth");
                    return TencentOAuth.iphoneQQInstalled();
                case "weixin":
                    var WXApi = plus.ios.import("WXApi");
                    return WXApi.isWXAppInstalled();
                case "sinaweibo":
                    var SinaAPI = plus.ios.import("WeiboSDK");
                    return SinaAPI.isWeiboAppInstalled();
                default:
                    break;
            }
        }
    };

    owner.groupForSize = function (collection, size) {
        var result = [];
        // default size to two item
        size = parseInt(size) || 2;

        // add each chunk to the result
        for (var x = 0; x < Math.ceil(collection.length / size); x++) {
            var start = x * size;
            var end = start + size;
            result.push(collection.slice(start, end));
        }
        return result;
    };

    owner.submitRequest = function (a_cfg) {
        var callback = a_cfg.callback;
        delete a_cfg.callback;
        a_cfg.cache = false;
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
            mui.toast("服務端錯誤信息:" + data.statusText, {
                duration: 'long',
                type: 'div'
            })
        };
        $.ajax(a_cfg);
    };

    owner.getObj = function (a_cfg) {
        var callback = a_cfg.callback;
        a_cfg.callback = function (cpData) {
            var data = cpData.data;
            callback && callback(data, cpData.code, cpData.desc);
        };
        owner.submitRequest(a_cfg);
    };

    owner.getAllData = function (a_cfg) {
        var callback = a_cfg.callback;
        a_cfg.callback = function (cpData) {
            var data = cpData.data;
            callback && callback(data, cpData.code, cpData.desc);
        };
        owner.submitRequest(a_cfg);
    };

    owner.parseFn = function (a_data) {
        var code, data, desc;
        if (a_data) {
            if (typeof a_data === 'string') {
                a_data = $.parseJSON(a_data);
            }
            a_data['data'] && (data = a_data['data']);
            code = a_data['code'];
            desc = a_data['desc'];
        }
        return {data: data, code: code, desc: desc}
    }
}(mui, window.app = {}));