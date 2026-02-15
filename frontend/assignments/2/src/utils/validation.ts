export const isValidEmail = (email: string): boolean => {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email);
};

export const isValidPhone = (phone: string): boolean => {
  const re = /^\d{10}$|^\d{3}-\d{3}-\d{4}$/;
  return re.test(phone);
};

export const isValidZip = (zip: string): boolean => {
  return /^\d{5}$/.test(zip);
};

export const isValidCard = (cardNumber: string): boolean => {
  return /^\d{16}$/.test(cardNumber.replace(/\s/g, ''));
};

export const isValidCVV = (cvv: string): boolean => {
  return /^\d{3,4}$/.test(cvv);
};