function y = quad_func_2_v0(x)
	lambda = 1;
	y = quad_func_2(x) + (lambda/2)*norm(x)^2;
end