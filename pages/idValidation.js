/**
 * 身份证/证件验证工具
 * 支持：国内身份证、护照、港澳身份证、台湾通行证
 */

// 国内身份证验证
export const validateChineseID = (id) => {
  if (!id || id.length !== 18) return false;
  
  const weight = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
  const check = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'];
  
  let sum = 0;
  for (let i = 0; i < 17; i++) {
    sum += parseInt(id[i]) * weight[i];
  }
  
  return id[17].toUpperCase() === check[sum % 11];
};

// 护照验证（简单规则）
export const validatePassport = (id) => {
  // 中国护照：E/P + 8位数字
  const chinesePassport = /^[EP][0-9]{8}$/;
  // 外国护照：字母+数字组合，至少6位
  const foreignPassport = /^[A-Za-z0-9]{6,20}$/;
  
  return chinesePassport.test(id) || foreignPassport.test(id);
};

// 香港身份证验证
export const validateHKID = (id) => {
  // 格式：1-2位字母 + 6位数字 + (A-Z或数字)
  const pattern = /^[A-Z]{1,2}[0-9]{6}[0-9A-Z]$/;
  if (!pattern.test(id)) return false;
  
  // 校验码计算
  const chars = id.toUpperCase().split('');
  let sum = 0;
  
  if (chars.length === 8) {
    sum += (chars[0].charCodeAt(0) - 64) * 8;
    for (let i = 1; i < 7; i++) {
      sum += parseInt(chars[i]) * (8 - i);
    }
  } else {
    sum += (chars[0].charCodeAt(0) - 64) * 9;
    sum += (chars[1].charCodeAt(0) - 64) * 8;
    for (let i = 2; i < 8; i++) {
      sum += parseInt(chars[i]) * (8 - i + 1);
    }
  }
  
  const checkDigit = chars[chars.length - 1];
  const checkValue = checkDigit === 'A' ? 10 : parseInt(checkDigit);
  
  return (sum + checkValue) % 11 === 0;
};

// 澳门身份证验证
export const validateMacauID = (id) => {
  // 格式：1位数字 + 6位数字 + 1位数字
  const pattern = /^[1-5][0-9]{6}[0-9]$/;
  return pattern.test(id);
};

// 台湾来往大陆通行证验证
export const validateTaiwanPass = (id) => {
  // 格式：8位数字
  const pattern = /^[0-9]{8}$/;
  return pattern.test(id);
};

// 通用验证入口
export const validateID = (id, type) => {
  switch (type) {
    case 'id_card':
      return validateChineseID(id);
    case 'passport':
      return validatePassport(id);
    case 'hk_id':
      return validateHKID(id);
    case 'macau_id':
      return validateMacauID(id);
    case 'taiwan_pass':
      return validateTaiwanPass(id);
    default:
      return false;
  }
};

export const IDTypeOptions = [
  { value: 'id_card', label: '居民身份证' },
  { value: 'passport', label: '护照' },
  { value: 'hk_id', label: '港澳居民来往内地通行证' },
  { value: 'macau_id', label: '澳门居民身份证' },
  { value: 'taiwan_pass', label: '台湾居民来往大陆通行证' },
];