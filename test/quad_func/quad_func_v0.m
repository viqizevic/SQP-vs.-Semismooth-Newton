function y = quad_func_v0(x)
	lambda = 1;
	y = quad_func(x) + (lambda/2)*norm(x)^2;
end