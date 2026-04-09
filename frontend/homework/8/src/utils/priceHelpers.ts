export const calculateDiscountPrice = (price: number, discountPercentage: number): number => {
  if (!discountPercentage) return price;
  const discounted = price * (1 - discountPercentage / 100);
  return parseFloat(discounted.toFixed(2));
};