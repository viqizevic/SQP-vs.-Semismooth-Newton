function y = quad_func_3_v0(x)
	lambda = 1;
	y = quad_func_3(x) + (lambda/2)*norm(x)^2;
end