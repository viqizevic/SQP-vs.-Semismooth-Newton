function y = quad_func_v0(x)
	lambda = 4;
	y = quad_func(x) + (lambda/2)*norm(x)^2;
end