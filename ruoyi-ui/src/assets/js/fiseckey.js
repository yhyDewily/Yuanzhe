/**
 * Description: 通用Ukey的js接口
 * Author: paulhou(houenchao)
 * Version: V1.1
 * Create: 2019/3/22 13:56
 */
var initurl = "http://127.0.0.1:8090/do";
var keyType = 4;
var ppid = 0;//sm3 hash时默认id
jQuery.support.cors = true;
if (isIE() && isSSL()) {
    initurl = "https://127.0.0.1:433/do";
    //alert(initurl);
}

function isSSL() {
    var url = window.location.href;
    if (url.indexOf("https") != -1) {
        return true;
    }
    return false;
}

function isIE() {
    var userAgent = navigator.userAgent;
    //alert(userAgent);
    var isIE = userAgent.indexOf("NET") > -1 && userAgent.indexOf("rv") > -1;
    isIE = isIE || (userAgent.indexOf("MSIE") > -1);
    return isIE;
}

export var FISECKEY = {
    OpenDevice: function () {
        if (arguments.length === 1) {
            var handle = 0;
            $.ajax({
                url: initurl,
                type: "post",
                dataType: "json",
                data: {
                    order: '01000001',
                    index: arguments[0],
                    keyType: 1
                },
                async: false,
                success: function (data) {
                    if (0 == data.rev) {
                        handle = data.hdev;
                    } else {
                        throw new Error("设备打开失败：" + FISECERR.getErrMsg(data.rev));
                    }
                },
                error: function (XMLHttpRequest, textStatus) {
                    throw new Error(XMLHttpRequest.status + ";" + textStatus);
                }
            });
            return handle;
        }
        if (arguments.length === 2) {
            var handle = 0;
            $.ajax({
                url: initurl,
                type: "post",
                dataType: "json",
                data: {
                    order: '01000001',
                    index: arguments[0],
                    keyType: arguments[1]
                },
                async: false,
                success: function (data) {
                    if (0 == data.rev) {
                        handle = data.hdev;
                    } else {
                        throw new Error("设备打开失败：" + FISECERR.getErrMsg(data.rev));
                    }
                },
                error: function (XMLHttpRequest, textStatus) {
                    throw new Error(XMLHttpRequest.status + ";" + textStatus);
                }
            });
            return handle;
        }
    },

    CloseDevice: function (hDev) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000002',
                hdevice: hDev
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("设备关闭失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    OpenByName: function () {
        if (arguments.length === 2) {
            var handle = 0;
            $.ajax({
                url: initurl,
                type: "post",
                dataType: "json",
                data: {
                    order: '01000003',
                    devName: arguments[0],
                    keyType: arguments[1]
                },
                async: false,
                success: function (data) {
                    if (0 == data.rev) {
                        handle = data.hdev;
                    } else {
                        throw new Error("设备打开失败：" + FISECERR.getErrMsg(data.rev));
                    }
                },
                error: function (XMLHttpRequest, textStatus) {
                    throw new Error(XMLHttpRequest.status + ";" + textStatus);
                }
            });
            return handle;
        }
        if (arguments.length === 3) {
            var handle = 0;
            $.ajax({
                url: initurl,
                type: "post",
                dataType: "json",
                data: {
                    order: '01000003',
                    devName: arguments[0],
                    keyType: arguments[1]
                },
                async: false,
                success: function (data) {
                    if (0 == data.rev) {
                        handle = data.hdev;
                    } else {
                        throw new Error("设备打开失败：" + FISECERR.getErrMsg(data.rev));
                    }
                },
                error: function (XMLHttpRequest, textStatus) {
                    throw new Error(XMLHttpRequest.status + ";" + textStatus);
                }
            });
            return handle;
        }
    },

    OpenBySerial: function () {
        if (arguments.length === 1) {
            var handle = 0;
            $.ajax({
                url: initurl,
                type: "post",
                dataType: "json",
                data: {
                    order: '01000004',
                    devSerial: arguments[0],
                    keyType: 1
                },
                async: false,
                success: function (data) {
                    if (0 == data.rev) {
                        handle = data.hdev;
                    } else {
                        throw new Error("设备打开失败：" + FISECERR.getErrMsg(data.rev));
                    }
                },
                error: function (XMLHttpRequest, textStatus) {
                    throw new Error(XMLHttpRequest.status + ";" + textStatus);
                }
            });
            return handle;
        }
        if (arguments.length === 2) {
            var handle = 0;
            $.ajax({
                url: initurl,
                type: "post",
                dataType: "json",
                data: {
                    order: '01000004',
                    devSerial: arguments[0],
                    keyType: arguments[1]
                },
                async: false,
                success: function (data) {
                    if (0 == data.rev) {
                        handle = data.hdev;
                    } else {
                        throw new Error("设备打开失败：" + FISECERR.getErrMsg(data.rev));
                    }
                },
                error: function (XMLHttpRequest, textStatus) {
                    throw new Error(XMLHttpRequest.status + ";" + textStatus);
                }
            });
            return handle;
        }
    },

    EnumByArray: function (keyType) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000005',
                keyType: keyType
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.EnumDeviceArrayList;
                } else {
                    throw new Error("设备枚举失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    EnumByName: function (keyType) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000006',
                keyType: keyType
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.EnumDeviceNameList;
                } else {
                    throw new Error("设备枚举失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    EnumBySerial: function (keyType) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000007',
                keyType: keyType
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.EnumDeviceSerelList;
                } else {
                    throw new Error("设备枚举失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    GetInfo: function (hDev, flag) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000008',
                hdevice: hDev,
                flag: flag
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.info;
                } else {
                    throw new Error("设备信息获取失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SetDevName: function (hDev, devName) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000009',
                hdevice: hDev,
                devName: devName
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("设置设备名称失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    GetDevName: function (hDev) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000063',
                hdevice: hDev
            },
            async: false,
            success: function (data) {
                console.log(data);
                if (0 == data.rev) {
                    result = data.devName;
                } else {
                    throw new Error("获取设备名称失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    GenRandom: function (hDev, len) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000010',
                hdevice: hDev,
                len: len
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.ranData;
                } else {
                    throw new Error("生成随机数失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    GenRSAKeypair: function (hDev, flag, keyLength, keyNum) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000011',
                hdevice: hDev,
                flag: flag,
                keyNum: keyNum,
                keyLen: keyLength
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("RSA密钥对生成失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    DelRSAKeypair: function (hDev, keyNum) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000012',
                hdevice: hDev,
                keyNum: keyNum
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("RSA密钥对删除失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ImportRSAKey: function (hDev, keyNum, flag, vWarppedkeyData, vRsakey) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000013',
                hdevice: hDev,
                keyNum: keyNum,
                flag: flag,
                WarppedkeyData: vWarppedkeyData,
                key: vRsakey
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("RSA密钥对导入失败:"
                        + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ExportRSAKey: function (hDev, keyNum, keyType) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000014',
                hdevice: hDev,
                keyNum: keyNum,
                keyType: keyType
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.pubkey;
                } else {
                    throw new Error("RSA密钥对导出失败:"
                        + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    // ExportRSAKeyBase64 : function(hDev, keyNum) {
    // 	var result;
    // 	$.ajax({
    // 		url: initurl,
    // 		type : "post",
    // 		dataType:"json",
    // 		data : {
    // 			order: '01000058',
    // 			hdevice:hDev,
    // 			keyNum:keyNum
    // 		},
    // 		async : false,
    // 		success : function(data){
    // 			if (0 == data.rev) {
    // 				result = data.pubkey;
    // 			} else {
    // 				throw new Error("RSA密钥对导出失败:"
    // 						+ FISECERR.getErrMsg(data.rev));
    // 			}
    // 		},
    // 		error : function(XMLHttpRequest, textStatus) {
    // 			throw new Error(XMLHttpRequest.status + ";" + textStatus);
    // 		}
    // 	});
    // 	return result;
    // },

    RSAEncrypt: function (hDev, keyNum, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000015',
                hdevice: hDev,
                keyNum: keyNum,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.enData;
                } else {
                    throw new Error("RSA加密失败:"
                        + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    RSADecrypt: function (hDev, keyNum, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000016',
                hdevice: hDev,
                keyNum: keyNum,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.decData;
                } else {
                    throw new Error("RSA解密失败:"
                        + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    RSASignData: function (hDev, keyNum, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000017',
                hdevice: hDev,
                keyNum: keyNum,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.sigData;
                } else {
                    throw new Error("RSA签名失败:"
                        + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    RSAVerify: function (hDev, keyNum, vHashAlg, inData, vSignData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000018',
                hdevice: hDev,
                keyNum: keyNum,
                alg: vHashAlg,
                inData: inData,
                signData: vSignData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("RSA验签失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    Hash: function (hDev, vHashAlg, inData) {
        var result;

        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000019',
                hdevice: hDev,
                flag: vHashAlg,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.digest;
                } else {
                    throw new Error("哈希运算失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SM3Data: function (hDev, keyNum, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000020',
                hdevice: hDev,
                keyNum: keyNum,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.digest;
                } else {
                    throw new Error("SM3运算失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    GenECCKeypair: function (hDev, vAlg, vKeyBits, keyNum) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000040',
                hdevice: hDev,
                alg: vAlg,
                keyNum: keyNum,
                keyLen: vKeyBits
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("ECC密钥对生成失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    DelECCKeypair: function (hDev, keyNum) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000041',
                hdevice: hDev,
                keyNum: keyNum,
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("ECC密钥对生成失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ImportECCKeypair: function (hDev, keyNum, vStrECCPrikey) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000042',
                hdevice: hDev,
                keyNum: keyNum,
                priKey: vStrECCPrikey
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("ECC密钥对导入失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ImportECCKeypairEX: function (hDev, keyNum, vStrECCPubkey, vStrECCPrikey) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000091',
                hdevice: hDev,
                keyNum: keyNum,
                priKey: vStrECCPrikey,
                pubKey: vStrECCPubkey
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("ECC密钥对EX导入失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ExportECCKeypair: function (hDev, keyNum, keyflag) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000043',
                hdevice: hDev,
                keyNum: keyNum,
                keyflag: keyflag
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.keyData;
                } else {
                    throw new Error("ECC密钥对导出失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ECCEncrypt: function (hDev, alg, keyNum, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000044',
                hdevice: hDev,
                alg: alg,
                keyNum: keyNum,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.enData;
                } else {
                    throw new Error("ECC加密失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ECCDecrypt: function (hDev, keyData, cipherData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000045',
                hdevice: hDev,
                keyData: keyData,
                cipherData: cipherData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.decData;
                } else {
                    throw new Error("ECC解密失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ECCEncryptDer: function (hDev, alg, keyNum, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000065',
                hdevice: hDev,
                alg: alg,
                keyNum: keyNum,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.enDataDer;
                } else {
                    throw new Error("ECC加密失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ECCDecryptDer: function (hDev, keyData, cipherData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000066',
                hdevice: hDev,
                keyData: keyData,
                cipherData: cipherData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.decDataDer;
                } else {
                    throw new Error("ECC解密失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ECCEncryptExt: function (hDev, alg, pubkey, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000090',
                hdevice: hDev,
                ppubkey: pubkey,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.enData;
                } else {
                    throw new Error("ECC加密失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ECCSign: function (hDev, alg, keyNum, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000046',
                hdevice: hDev,
                keyNum: keyNum,
                inData: inData,
                alg: alg
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.sigData;
                } else {
                    throw new Error("ECC签名失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ECCVerify: function (hDev, vAlg, keyNum, inData, vSignData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000047',
                hdevice: hDev,
                keyNum: keyNum,
                alg: vAlg,
                inData: inData,
                signData: vSignData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("ECC验签失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ContainerWriteCert: function (hDev, vFlag, vContainerName, vCertData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000061',
                hdevice: hDev,
                cintainer: vContainerName,
                flag: vFlag,
                cert: vCertData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("证书写入失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ContainerReadCert: function (hDev, vFlag, vContainerName) {
        var result;

        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000062',
                hdevice: hDev,
                cintainer: vContainerName,
                flag: vFlag
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.data;
                } else {
                    throw new Error("证书读取失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ContainerEnum: function (hDev) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000052',
                hdevice: hDev
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.conlist;
                } else {
                    throw new Error("证书容器枚举失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ContainerDelete: function (hDev, vContainerName) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000051',
                hdevice: hDev,
                container: vContainerName
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("证书容器删除失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    USER_Login: function (hDev, vstrPin) {
        var result = ""
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000027',
                hdevice: hDev,
                pin: vstrPin
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.rev;
                } else {
                    var pinErr = "" + data.rev.toString(16);
                    if (pinErr.match(/.*\83$/)) {
                        throw new Error("用户PIN码错误，剩余尝试次数：" + data.count);
                    } else {
                        throw new Error("用户登录失败，" + FISECERR.getErrMsg(data.rev));
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return 0;
    },

    USER_Logout: function (hDevice, vuser) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000028',
                hdevice: hDevice,
                user: vuser
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("用户登出失败:" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ADMIN_Login: function (hDev, vstrPin) {
        var result = null
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000064',
                hdevice: hDev,
                pin: vstrPin
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.rev;
                } else {
                    var pinErr = "" + data.rev.toString(16);
                    if (pinErr.match(/.*\83$/)) {
                        throw new Error("管理员PIN码错误，剩余尝试次数：" + data.count);
                    } else {
                        throw new Error("管理员登录失败，" + FISECERR.getErrMsg(data.rev));
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ADMIN_Logout: function (hDevice, vuser) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000028',
                hdevice: hDevice,
                user: vuser
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    alert("管理员登出成功")
                } else {
                    alert("管理员登出失败");
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    USER_ChangePin: function (hDev, vflag, vstrOldPin, vstrNewPin) {
        //vflag 1修改操作员口令2修改管理员口令3解锁
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000029',
                hdevice: hDev,
                flag: vflag,
                oldPin: vstrOldPin,
                pin: vstrNewPin
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    if (!(1001 == data.rev || 1002 == data.rev)) {
                        throw new Error("修改PIN码失败，剩余重试次数：" + data.count);
                    } else {
                        throw new Error("修改PIN码错误：" + FISECERR.getErrMsg(data.rev));
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    GenKey: function (hDev, vAlg, index) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000022',
                hdevice: hDev,
                alg: vAlg,
                keyNum: index
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("对称密钥生成失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    DelKey: function (hDevice, index) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000023',
                hdevice: hDevice,
                keyNum: index
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("对称密钥删除失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ImportKey: function (hDevice, viAlg, vstrKey, index) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000021',
                hdevice: hDevice,
                alg: viAlg,
                keyNum: index,
                key: vstrKey
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("对称密钥导入失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    Encrypt: function (hDevice, alg, index, vWorkMode, vstrIn, vstrIv, vstrKey) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000025',
                hdevice: hDevice,
                alg: alg,
                keyNum: index,
                mode: vWorkMode,
                inData: vstrIn,
                iv: vstrIv,
                inKey: vstrKey
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.enData;
                } else {
                    throw new Error("对称加密失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });

        return result;
    },

    Decrypt: function (hDevice, alg, index, vWorkMode, vstrChiper, vstrIv, vstrKey) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000026',
                hdevice: hDevice,
                alg: alg,
                keyNum: index,
                mode: vWorkMode,
                inData: vstrChiper,
                iv: vstrIv,
                inKey: vstrKey
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.decData;
                } else {
                    throw new Error("对称解密失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });

        return result;
    },

    FILE_Init: function (hDevice) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000031',
                hdevice: hDevice
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("文件初始化失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });

    },

    FILE_CreateDir: function (hDevice, vstrDir, vdwAccCon) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000032',
                hdevice: hDevice,
                dir: vstrDir,
                acc: vdwAccCon
            },
            async: false,
            success: function (data) {

                if (0 != data.rev) {
                    throw new Error("路径创建失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    FILE_CreateFile: function (hDevice, vstrDir, vstrfile, vdwSize, vdwAccCon) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000034',
                hdevice: hDevice,
                dir: vstrDir,
                file: vstrfile,
                size: vdwSize,
                acc: vdwAccCon
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("文件创建失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    FILE_DeleteDir: function (hDevice, vstrDir) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000033',
                hdevice: hDevice,
                dir: vstrDir
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("路径删除失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    FILE_DeleteFile: function (hDevice, vstrDir, vstrfile) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000037',
                hdevice: hDevice,
                dir: vstrDir,
                file: vstrfile
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("文件删除失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    FILE_EnmuDir: function (hDevice, vstrDir) {
        var dirlist = "";
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000038',
                hdevice: hDevice,
                dir: vstrDir
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    dirlist = data.data;
                } else {
                    throw new Error("路径枚举失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return dirlist;
    },

    FILE_EnmuFile: function (hDevice) {
        var dirlist = "";
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000039',
                hdevice: hDevice,
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    dirlist = data.data;
                } else {
                    throw new Error("文件枚举失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return dirlist;
    },

    FILE_ReadFile: function (hDevice, vstrDir, vstrfile, vdwOffSet, vdwSize) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000035',
                hdevice: hDevice,
                dir: vstrDir,
                file: vstrfile,
                offset: vdwOffSet,
                size: vdwSize
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.data;
                } else {
                    throw new Error("文件读取失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    FILE_WriteFile: function (hDevice, vstrDir, vstrfile, vdwOffSet, vdwSize, vstrData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '01000036',
                hdevice: hDevice,
                dir: vstrDir,
                file: vstrfile,
                offset: vdwOffSet,
                size: vdwSize,
                input: vstrData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("文件写入失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    FILE_GetInfo: function (hDevice, vstrDir, vstrfile) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000053',
                hdevice: hDevice,
                vstrDir: vstrDir,
                vstrfile: vstrfile
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.fileinfo;
                } else {
                    throw new Error("文件获取信息失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    getSerialAndSubjects: function (hDevice) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000054',
                hdevice: hDevice
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.sersublist;
                } else {
                    throw new Error("获取证书信息失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    EnvelopeDecode: function (hDevice, alg, vWorkMode, vstrIv, vEccCipher, vEnvelopeData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000067',
                hdevice: hDevice,
                alg: alg,
                mode: vWorkMode,
                iv: vstrIv,
                cipher: vEccCipher,
                envelopeData: vEnvelopeData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.outData;
                } else {
                    throw new Error("EnvelopedData失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    //p10
    CreateP10Request: function (hDevice, vkeyalg, vsignalg, vkeynum, vkeybits, vcountry, vprovince, vlocality, vorganization, vunit, vcommon, vkeydata) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000068',
                hdevice: hDevice,
                keyalg: vkeyalg,
                signalg: vsignalg,
                keynum: vkeynum,
                keybits: vkeybits,
                country: vcountry,
                province: vprovince,
                locality: vlocality,
                organization: vorganization,
                unit: vunit,
                common: vcommon,
                keydata: vkeydata
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.P10;
                } else {
                    throw new Error("CreateP10Request error" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    //p7bvalid
    P7bCertLinkValid: function (hDevice, vp7bcert, vusercert) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000069',
                hdevice: hDevice,
                p7bcert: vp7bcert,
                usercert: vusercert
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.rev;
                } else {
                    throw new Error("P7bCertLinkValid error" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },


    //SM9
    GenSM9MasterKey: function (hDevice, vtype, vkeynum) {
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000070',
                hdevice: hDevice,
                type: vtype,
                keynum: vkeynum
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("GenSM9MasterKey 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    DelSM9MasterKey: function (hDevice, vtype, vkeynum) {
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000071',
                hdevice: hDevice,
                type: vtype,
                keynum: vkeynum
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("DelSM9MasterKey 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ExportSM9MasterKey: function (hDevice, vtype, vkeynum) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000072',
                hdevice: hDevice,
                type: vtype,
                keynum: vkeynum
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.pubkey;
                } else {
                    throw new Error("ExportSM9MasterKey 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ImportSM9MasterKey: function (hDevice, vtype, vkeynum, prikey, pubkey) {
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000073',
                hdevice: hDevice,
                type: vtype,
                keynum: vkeynum,
                pubKey: pubkey,
                priKey: prikey
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("ImportSM9MasterKey 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    GenSM9UserKey: function (hDevice, vtype, vID, vkeynum, vmkeynum, vmprikey) {
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000074',
                hdevice: hDevice,
                type: vtype,
                id: vID,
                keynum: vkeynum,
                mkeynum: vmkeynum,
                prikey: vmprikey
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("GenSM9UserKey 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    DelSM9UserKey: function (hDevice, vtype, vkeynum) {
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000075',
                hdevice: hDevice,
                type: vtype,
                keynum: vkeynum
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("DelSM9EncUserKey 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    ImportSM9UserKey: function (hDevice, vtype, vkeynum, prikey, pubkey) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000077',
                hdevice: hDevice,
                type: vtype,
                keynum: vkeynum,
                pubKey: pubkey,
                priKey: prikey
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("ImportSM9UserKey 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    ExportSM9UserKey: function (hDevice, vtype, vkeynum) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000076',
                hdevice: hDevice,
                type: vtype,
                keynum: vkeynum
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.userid;
                } else {
                    throw new Error("ExportSM9UserKey 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SM9Encrypt: function (hDevice, vKeyNum, vMKeyNum, vUserId, vMsatPubKey, vInData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000078',
                hDevice: hDevice,
                keyNum: vKeyNum,
                mastKeyNum: vMKeyNum,
                userId: vUserId,
                mastPubKey: vMsatPubKey,
                inData: vInData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.encData;
                } else {
                    throw new Error("SM9Encrypt 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SM9Decrypt: function (hDevice, vKeyNum, vUserID, vInData, vUserPriKey) {
		let result;
		$.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000079',
                hDevice: hDevice,
                keyNum: vKeyNum,
                userID: vUserID,
                inData: vInData,
                userPriKey: vUserPriKey
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.decData;
                } else {
                    throw new Error("SM9Decrypt 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SM9Sign: function (hDevice, vKeyNum, vMastkeyNum, vMastPubKey, vUserPriKey, vInData) {
		let result;
		$.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000080',
                hDevice: hDevice,
                keyNum: vKeyNum,
                mastKeyNum: vMastkeyNum,
                mastPubKey: vMastPubKey,
                userPriKey: vUserPriKey,
                inData: vInData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.signData;
                } else {
                    throw new Error("SM9Sign 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SM9Verify: function (hDevice, vKeyNum, vMastKeyNum, vUserID, vMastPubKey, vInData, vSignature) {
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000081',
                hDevice: hDevice,
                keyNum: vKeyNum,
                mastKeyNum: vMastKeyNum,
                userID: vUserID,
                mastPubKey: vMastPubKey,
                inData: vInData,
                gignature: vSignature
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("SM9Verify 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SM9KeyEncapsulePack: function (hDevice, vKeyNum, vMastKeyNum, vUserID, vKeyLen, vMastPubkey, vSymKeyNum) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000086',
                hDevice: hDevice,
                keyNum: vKeyNum,
                mastKeyNum: vMastKeyNum,
                userID: vUserID,
                keyLen: vKeyLen,
                mastPubkey: vMastPubkey,
                symKeyNum: vSymKeyNum
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.packData;
                } else {
                    throw new Error("SM9KeyEncapsulePack 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SM9KeyEncapsuleUnPack: function (hDevice, vKeyNum, vUserID, vKeyLen, vUsrPrikey, vKeyPackage, vSymKeyNum) {
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000087',
                hDevice: hDevice,
                keyNum: vKeyNum,
                userID: vUserID,
                keyLen: vKeyLen,
                usrPrikey: vUsrPrikey,
                keyPackage: vKeyPackage,
                symKeyNum: vSymKeyNum
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("SM9KeyEncapsuleUnPack 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SM9GenSessionKey: function (hDevice, vKeyNum, vUserID, vMastPubkey) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000088',
                hDevice: hDevice,
                keyNum: vKeyNum,
                userID: vUserID,
                MastPubkey: vMastPubkey
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.cipher;
                } else {
                    throw new Error("SM9GenSessionKey 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SM9ImportSessionKey: function (hDevice, vKeyNum, vUKeyNum, vUserID, vUsrEncPrikey, vUsrSigPrikey, vEnchash, vSighash) {
        $.ajax({
            url: initurl,
            type: "post",
            data: {
                order: '01000089',
                hDevice: hDevice,
                keyNum: vKeyNum,
                UKeyNum: vUKeyNum,
                userID: vUserID,
                UsrEncPrikey: vUsrEncPrikey,
                UsrSigPrikey: vUsrSigPrikey,
                Enchash: vEnchash,
                Sighash: vSighash
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("SM9ImportSessionKey 失败" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    }
}

var FISECERR = {
    getErrMsg: function (errCode) {
        switch (errCode) {
            case 0x001:
                return "fail";
            case 0x002:
                return "part ok";
            case 0x003:
                return "unknown error";
            case 0x005:
                return "error parameter";
            case 0x006:
                return "error right";
            case 0x007:
                return "device busy";
            case 0x008:
                return "operation timeout";
            case 0x009:
                return "no mem";
            case 0x00a:
                return "no resouce";
            case 0x00b:
                return "communication error";
            case 0x00c:
                return "register access error";
            case 0x00d:
                return "stack is overflow";
            case 0x00e:
                return "device is used now";
            case 0x00f:
                return "open session is out of range";
            case 0x010:
                return "dma read error";
            case 0x011:
                return "dma write error";
            case 0x012:
                return "create sync objcect error";
            case 0x013:
                return "get sync objcect error";
            case 0x014:
                return "release sync objcect error";
            case 0x015:
                return "data length error";
            case 0x016:
                return "key length error or not support";
            case 0x017:
                return "dev not close";
            case 0x0a0:
                return "key is not exist";
            case 0x0a1:
                return "no free key handle";
            case 0x0a2:
                return "key handle is out of range";
            case 0x0a3:
                return "alg step err(init/update/final) ";
            case 0x100:
                return "iv length error";
            case 0x180:
                return "ECC not init";
            case 0x181:
                return "ECC pubkey error";
            case 0x182:
                return "ECC prikey error";
            case 0x183:
                return "ECC sign error";
            case 0x184:
                return "ECC verify error";
            case 0x185:
                return "ECC encrypt error";
            case 0x186:
                return "ECC decrypt error";
            case 0x187:
                return "ECC decrypt verify error";
            case 0x188:
                return "ECC agreement error";
            case 0x200:
                return "SM3 user'ID length is out of range";
            case 0x240:
                return "file system is not init";
            case 0x241:
                return "dir nested too deep";
            case 0x242:
                return "dir is not exist";
            case 0x243:
                return "file is not exist";
            case 0x244:
                return "dir has already exist";
            case 0x245:
                return "file has already exist";
            case 0x246:
                return "dir number is out of range";
            case 0x247:
                return "file number is out of range";
            case 0x248:
                return "file space is not enough";
            case 0x249:
                return "file operation is out of range";
            case 0x260:
                return "flash operation is timeout";
            case 0x261:
                return "flash write error";
            case 0x262:
                return "flash read error";
            case 0x263:
                return "flash operation is out of range";
            case 0x264:
                return "eeprom operation is timeout";
            case 0x265:
                return "eeprom write error";
            case 0x266:
                return "eeprom read error";
            case 0x267:
                return "eeprom operation is out of range";
            case 0x280:
                return "user is not exist";
            case 0x281:
                return "user has already exist";
            case 0x282:
                return "user number is out of range";
            case 0x283:
                return "user pin err";
            case 0x284:
                return "backup step err";
            case 0x285:
                return "open user dev err";
            case 0x286:
                return "get user dev info err";
            case 0x287:
                return "write user dev mem err";
            case 0x288:
                return "read user dev mem err";
            case 0x289:
                return "user is not login";
            case 0xF902005:
                return "PIN码长度范围8-16";
            case 1001:
                return "param is null";
            case 1002:
                return "paramValue is null";
            case 1003:
                return "param is error";
            case 1004:
                return "malloc is error";
            case 1005:
                return "interface is failed";
            default:
                return "not support";
        }
    }
}

export var SKFKEY = {
    SKF_EnumDev: function (falg, pulSize) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000004',
                falg: flag,
                pulSize: pulSize
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.nameParam;
                } else {
                    throw new Error("设备枚举失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ConnectDev: function (devName, provider) {
        var handle = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000005',
                devName: devName,
                provider: provider
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    handle = data.hdev;
                } else {
                    throw new Error("连接设备失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return handle;
    },

    SKF_DisConnect: function (hDev) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000006',
                hDevice: hDev
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("断开设备失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_GetDevState: function (devName) {
        var state = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000007',
                devName: devName
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    state = data.state;
                } else {
                    throw new Error("判断设备状态失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return state;
    },

    SKF_SetLabel: function (hDev, szLabel) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000008',
                hdevice: hDev,
                szLabel: szLabel
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("设置设备标签失败:" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_GetDevInfo: function (hDev) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000009',
                hdevice: hDev
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.devInfo;
                } else {
                    throw new Error("获取设备信息失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_LockDev: function (hDev, timeOut) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000010',
                hDevice: hDev,
                timeOut: timeOut
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("锁定设备失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_UnlockDev: function (hDev) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000011',
                hDevice: hDev
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("释放设备失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_ChangeDevAuthKey: function (hDev, keyValue) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000012',
                hdevice: hDev,
                keyValue: keyValue
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("更改设备认证密钥失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_DevAuth: function (hDev, authData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000013',
                hdevice: hDev,
                authData: authData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("设备认证失败：" + FISECERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_ChangePIN: function (hApplication, pinType, vstrOldPin, vstrNewPin) {
        var retryCount = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000014',
                hApplication: hApplication,
                pinType: pinType,
                oldPin: vstrOldPin,
                newPin: vstrNewPin
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    retryCount = data.count;
                } else {
                    throw new Error("修改PIN码错误：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return retryCount;
    },

    SKF_GetPINInfo: function (hApplication, pinType) {
        var result = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000015',
                hApplication: hApplication,
                pinType: pinType
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.result;
                } else {
                    throw new Error("获取PIN码信息失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_VerifyPIN: function (hApplication, pinType, vstrPin) {
        var retryCount = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000016',
                hApplication: hApplication,
                pinType: pinType,
                vstrPin: vstrPin
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    retryCount = data.count;
                } else {
                    throw new Error("校验PIN码失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return retryCount;
    },

    SKF_UnblockPIN: function (hApplication, szAdminPIN, szNewUserPIN) {
        var retryCount = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000017',
                hApplication: hApplication,
                szAdminPIN: szAdminPIN,
                szNewUserPIN: szNewUserPIN
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    retryCount = data.count;
                } else {
                    throw new Error("解锁用户PIN码失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return retryCount;
    },

    SKF_ClearSecureState: function (hApplication) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000018',
                hApplication: hApplication
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("清除安全状态失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_CreateApplication: function (hDev, appName, adminPin, adminReCount, userPin, userReCount, createFileRight) {
        var appHandle = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000019',
                hDev: hDev,
                appName: appName,
                adminPin: adminPin,
                adminReCount: adminReCount,
                userPin: userPin,
                userReCount: userReCount,
                createFileRight: createFileRight
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    appHandle = data.hApp;
                } else {
                    throw new Error("创建应用失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return appHandle;
    },

    SKF_EnumApplication: function (hDev, falg, pulSize) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000020',
                hdevice: hDev,
                falg: flag,
                pulSize: pulSize
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.nameParam;
                } else {
                    throw new Error("应用枚举失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_DeleteApplication: function (hDev, devName) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000021',
                hdevice: hDev,
                devName: devName
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("删除应用失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_OpenApplication: function (hDev, devName) {
        var appHandle = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000022',
                hdevice: hDev,
                devName: devName
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    appHandle = data.hApp;
                } else {
                    throw new Error("打开应用失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return appHandle;
    },

    SKF_CloseApplication: function (hApplication) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000023',
                hApplication: hApplication
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("关闭应用失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_CreateFile: function (hApplication, fileName, fileSize, readRight, writeRight) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000024',
                hApplication: hApplication,
                fileName: fileName,
                fileSize: fileSize,
                readRight: readRight,
                writeRight: writeRight
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("创建文件失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_DeleteFile: function (hApplication, fileName) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000025',
                hApplication: hApplication,
                fileName: fileName
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("删除文件失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_EnumFiles: function (hApplication, falg, pulSize) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000026',
                hApplication: hApplication,
                falg: flag,
                pulSize: pulSize
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.nameParam;
                } else {
                    throw new Error("文件枚举失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_GetFileInfo: function (hApplication, fileName) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000027',
                hApplication: hApplication,
                fileName: fileName
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.fileInfo;
                } else {
                    throw new Error("获取文件信息失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ReadFile: function (hApplication, fileName, offset, size, outLen) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000028',
                hApplication: hApplication,
                fileName: fileName,
                offset: offset,
                size: size,
                outLen: outLen
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.outData;
                } else {
                    throw new Error("读取文件数据失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_WriteFile: function (hApplication, fileName, offset, data, size) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000029',
                hApplication: hApplication,
                fileName: fileName,
                offset: offset,
                data: data,
                size: size
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("写入文件数据失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_CreateContainer: function (hApplication, conName) {
        var conHandle = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000030',
                hApplication: hApplication,
                conName: conName
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    conHandle = data.hCon;
                } else {
                    throw new Error("创建密钥容器失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return conHandle;
    },

    SKF_DeleteContainer: function (hApplication, conName) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000031',
                hApplication: hApplication,
                conName: conName
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    throw new Error("删除密钥容器失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_OpenContainer: function (hApplication, conName) {
        var conHandle = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000032',
                hApplication: hApplication,
                conName: conName
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    conHandle = data.hCon;
                } else {
                    throw new Error("打开密钥容器失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return conHandle;
    },

    SKF_CloseContainer: function (hContainer) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000033',
                hContainer: hContainer
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("关闭密钥容器失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_EnumContainer: function (hApplication, falg, pulSize) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000034',
                hApplication: hApplication,
                falg: flag,
                pulSize: pulSize
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.nameParam;
                } else {
                    throw new Error("枚举密钥容器失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_GetContainerType: function (hContainer) {
        var conType = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000035',
                hContainer: hContainer
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    conType = data.conType;
                } else {
                    throw new Error("获取密钥容器类型失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return conType;
    },

    SKF_ImportCertificate: function (hContainer, flag, cert) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000036',
                hContainer: hContainer,
                flag: flag,
                cert: cert
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("导入数字证书失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_ExportCertificate: function (hContainer, flag, certLen) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000037',
                hContainer: hContainer,
                flag: flag,
                certLen: certLen
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.outData;
                } else {
                    throw new Error("导出数字证书失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_GenRandom: function (hDev, len) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000038',
                hdevice: hDev,
                len: len
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.ranData;
                } else {
                    throw new Error("生成随机数失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_GenExtRSAKey: function (hDev, bits) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000039',
                hdevice: hDev,
                bits: bits
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.priKey;
                } else {
                    throw new Error("生成RSA密钥对并明文输出失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_GenRSAKeyPair: function (hContainer, bits) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000040',
                hContainer: hContainer,
                bits: bits
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.pubKey;
                } else {
                    throw new Error("生成RSA签名密钥对输出签名公钥失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ImportRSAKeyPair: function (hContainer, algId, wrappedKey, encryptedData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000041',
                hContainer: hContainer,
                algId: algId,
                wrappedKey: wrappedKey,
                encryptedData: encryptedData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("导入RSA加密公私钥对失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_RSASignData: function (hContainer, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000042',
                hContainer: hContainer,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.signData;
                } else {
                    throw new Error("RSA签名失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_RSAVerify: function (hDev, pubKey, inData, signData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000043',
                hdevice: hDev,
                pubKey: pubKey,
                inData: inData,
                signData: signData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("RSA验签失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_RSAExportSessionKey: function (hContainer, algId, pubKey, keyLen) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000044',
                hContainer: hContainer,
                algId: ulAlgId,
                pubKey: pubKey,
                keyLen: keyLen
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.data;
                } else {
                    throw new Error("生成会话密钥失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ExtRSAPubKeyOperation: function (hDev, flag, pubKey, inData) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000045',
                hdevice: hDev,
                flag: flag,
                pubKey: pubKey,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.outData;
                } else {
                    throw new Error("RSA外来公钥运算失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ExtRSAPriKeyOperation: function (hDev, flag, priKey, inData) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000046',
                hdevice: hDev,
                flag: flag,
                priKey: priKey,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.outData;
                } else {
                    throw new Error("RSA外来私钥运算失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_GenECCKeyPair: function (hContainer, ulAlgId) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000048',
                hContainer: hContainer,
                ulAlgId: ulAlgId
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.pubKey;
                } else {
                    throw new Error("生成ECC签名密钥对输出签名公钥失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_GenExtECCKey: function (hContainer, ulBitsLen) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000049',
                hContainer: hContainer,
                ulBitsLen: ulBitsLen
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.keyPair;
                } else {
                    throw new Error("生成外部ECC签名密钥对：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ImportECCKeyPair: function (hContainer, envelopedKeyBlob) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000050',
                hContainer: hContainer,
                envelopedKeyBlob: envelopedKeyBlob
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("导入ECC公私钥对失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_ECCSignData: function (hContainer, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000051',
                hContainer: hContainer,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.signData;
                } else {
                    throw new Error("ECC数字签名失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ECCVerify: function (hDev, pubKey, inData, signData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000052',
                hdevice: hDev,
                pubKey: pubKey,
                inData: inData,
                signData: signData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("ECC公钥验签失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_ECCExportSessionKey: function (hContainer, ulAlgId, pubKey) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000053',
                hContainer: hContainer,
                ulAlgId: ulAlgId,
                pubKey: pubKey
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.data;
                } else {
                    throw new Error("导出会话密钥失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ExtECCEncrypt: function (hDev, pubKey, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000054',
                hdevice: hDev,
                pubKey: pubKey,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.cipher;
                } else {
                    throw new Error("ECC外部公钥加密失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ExtECCDecrypt: function (hDev, priKey, cipher) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000055',
                hdevice: hDev,
                priKey: priKey,
                cipher: cipher
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.outData;
                } else {
                    throw new Error("ECC外部私钥解密失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ExtECCSign: function (hDev, priKey, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000057',
                hdevice: hDev,
                priKey: priKey,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.signData;
                } else {
                    throw new Error("ECC外部私钥签名失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ExtECCVerify: function (hDev, pubKey, inData, signData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000058',
                hdevice: hDev,
                pubKey: pubKey,
                inData: inData,
                signData: signData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("ECC外部公钥验签失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_ExportPublicKey: function (hDev, flag, signFlag) {
        var result = null;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000062',
                hdevice: hDev,
                flag: flag,
                signFlag: signFlag
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.keyData;
                } else {
                    throw new Error("导出容器公钥失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_ImportSessionKey: function (hContainer, ulAlgId, wrapedData) {
        var keyHandle = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000063',
                hContainer: hContainer,
                ulAlgId: ulAlgId,
                wrapedData: wrapedData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    keyHandle = data.phKey;
                } else {
                    throw new Error("导入会话密钥失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return keyHandle;
    },

    SKF_SetSymmKey: function (hDev, keyData, ulAlgId) {
        var keyHandle = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000064',
                hdevice: hDev,
                keyData: keyData,
                ulAlgId: ulAlgId
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    keyHandle = data.phKey;
                } else {
                    throw new Error("设置明文会话密钥失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return keyHandle;
    },

    SKF_EncryptInit: function (hKey, encParam) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000065',
                hKeyHandle: hKey,
                encParam: encParam
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("加密初始化失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_Encrypt: function (hKey, flag, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000066',
                hKeyHandle: hKey,
                flag: flag,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.encData;
                } else {
                    throw new Error("单组数据加密失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_EncryptUpdate: function (hKey, inData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000067',
                hKeyHandle: hKey,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.encData;
                } else {
                    throw new Error("多组数据加密失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_EncryptFinal: function (hKey) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000068',
                hKeyHandle: hKey
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.encData;
                } else {
                    throw new Error("结束加密失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_DecryptInit: function (hKey, decParam) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000069',
                hKeyHandle: hKey,
                decParam: decParam
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("解密初始化失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_Decrypt: function (hKey, flag, encryptedData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000070',
                hKeyHandle: hKey,
                flag: flag,
                encryptedData: encryptedData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.pbData;
                } else {
                    throw new Error("单组数据解密失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_DecryptUpdate: function (hKey, encryptedData) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000071',
                hKeyHandle: hKey,
                encryptedData: encryptedData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.pbData;
                } else {
                    throw new Error("多组数据解密失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_DecryptFinal: function (hKey) {
        var result;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000072',
                hKeyHandle: hKey
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.pbData;
                } else {
                    throw new Error("结束解密失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_DigestInit: function (hDev, ulAlgId, pubKey, id) {
        var hashHandle = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000073',
                hdevice: hDev,
                ulAlgId: ulAlgId,
                pubKey: pubKey,
                id: id
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    hashHandle = data.hHash;
                } else {
                    throw new Error("Hash初始化失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return hashHandle;
    },

    SKF_Digest: function (hHash, flag, inData) {
        var result = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000074',
                hHash: hHash,
                flag: flag,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.hashData;
                } else {
                    throw new Error("单组数据Hash失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_DigestUpdate: function (hHash, inData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000075',
                hHash: hHash,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("多组数据Hash失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_DigestFinal: function (hHash, flag) {
        var result = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000076',
                hHash: hHash,
                flag: flag
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.hashData;
                } else {
                    throw new Error("结束Hash失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_MacInit: function (hKey, macParam) {
        var macHandle = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000077',
                hKeyHandle: hKey,
                macParam: macParam
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    macHandle = data.hMac;
                } else {
                    throw new Error("Mac初始化失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return macHandle;
    },

    SKF_Mac: function (hMac, flag, inData) {
        var result = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000078',
                hMac: hMac,
                flag: flag,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.macData;
                } else {
                    throw new Error("单组数据Mac失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_MacUpdate: function (hMac, inData) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000079',
                hMac: hMac,
                inData: inData
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("多组数据Mac失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    },

    SKF_MacFinal: function (hMac) {
        var result = 0;
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000080',
                hMac: hMac
            },
            async: false,
            success: function (data) {
                if (0 == data.rev) {
                    result = data.macData;
                } else {
                    throw new Error("结束Mac失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
        return result;
    },

    SKF_CloseHandle: function (hKey) {
        $.ajax({
            url: initurl,
            type: "post",
            dataType: "json",
            data: {
                order: '02000081',
                hKeyHandle: hKey
            },
            async: false,
            success: function (data) {
                if (0 != data.rev) {
                    throw new Error("关闭密码对象句柄失败：" + SKFERR.getErrMsg(data.rev));
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                throw new Error(XMLHttpRequest.status + ";" + textStatus);
            }
        });
    }

}

var SKFERR = {
    getErrMsg: function (errCode) {
        switch (errCode) {
            case 0x00000000:
                return "success";
            case 0x0A000001:
                return "fail";
            case 0x0A000002:
                return "unknown error";
            case 0x0A000003:
                return "not support yeterr ";
            case 0x0A000004:
                return "file error";
            case 0x0A000005:
                return "invalid handle error";
            case 0x0A000006:
                return "invalid parameter error";
            case 0x0A000007:
                return "read file error";
            case 0x0A000008:
                return "write file error";
            case 0x0A000009:
                return "name len error";
            case 0x0A00000A:
                return "key usage error";
            case 0x0A00000B:
                return "modulus len error";
            case 0x0A00000C:
                return "not initialize error";
            case 0x0A00000D:
                return "object error";
            case 0x0A00000E:
                return "memory error";
            case 0x0A00000F:
                return "time out error";
            case 0x0A000010:
                return "indata len error";
            case 0x0A000011:
                return "indata error";
            case 0x0A000012:
                return "gen random error";
            case 0x0A000013:
                return "hash object error";
            case 0x0A000014:
                return "hash error";
            case 0x0A000015:
                return "gen rsa key error";
            case 0x0A000016:
                return "rea modulus len error";
            case 0x0A000017:
                return "csp import pubkey error";
            case 0x0A000018:
                return "rsa encrypt error";
            case 0x0A000019:
                return "rsa decrypt error";
            case 0x0A00001A:
                return "hash not equal error";
            case 0x0A00001B:
                return "key not fount error";
            case 0x0A00001C:
                return "cert not fount error";
            case 0x0A00001D:
                return "not export error";
            case 0x0A00001E:
                return "decrypt pad error";
            case 0x0A00001F:
                return "mac len error";
            case 0x0A000020:
                return "buffer too small";
            case 0x0A000021:
                return "key info type error";
            case 0x0A000022:
                return "not event error";
            case 0x0A000023:
                return "device removed";
            case 0x0A000024:
                return "pin incorrect";
            case 0x0A000025:
                return "pin locked";
            case 0x0A000026:
                return "pin invalid";
            case 0x0A000027:
                return "pin len range";
            case 0x0A000028:
                return "user already logged in";
            case 0x0A000029:
                return "user pin not initialized";
            case 0x0A00002A:
                return "user type invalid";
            case 0x0A00002B:
                return "application name invalid";
            case 0x0A00002C:
                return "application exists";
            case 0x0A00002D:
                return "user not logged in";
            case 0x0A00002E:
                return "application not exists";
            case 0x0A00002F:
                return "file already exist";
            case 0x0A000030:
                return "no room";
            case 0x0A000031:
                return "file not exist";
            case 0x0A000032:
                return "reach max container count";
            case 0x0A000033:
                return "no auth";
            case 1001:
                return "param is null";
            case 1002:
                return "paramValue is null";
            case 1003:
                return "param is error";
            case 1004:
                return "malloc is error";
            case 1005:
                return "interface is failed";
            default:
                return "not support";
        }
    }
}

