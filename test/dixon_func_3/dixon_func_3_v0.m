function y = dixon_func_3_v0(x)
	lambda = 0.01;
	y = dixon_func_3(x) + (lambda/2)*norm(x)^2;
end