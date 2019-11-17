con.setAutoCommit(false);

SELECT SUM(appliances.product_unit.stock)
FROM appliances.product_unit
GROUP BY appliances.product_id
WHERE appliances.product_id = " + productId + ";


if(appliances.product_unit.stock < totalStock
	con.rollback();
	return false;
	
for-loop, 
	SELECT appliances.product_unit.id, appliances.product_unit.stock
	FROM appliances.product_unit
	WHERE appliances.product_unit.product_id = " + productId + "
	ORDER BY appliances.product_unit.stock DESC LIMIT 1;
	
	update table product_unit and take stock from the selected table with above id
	remove same amount from totalStock variable

add row to customer_purchases

con.commit()