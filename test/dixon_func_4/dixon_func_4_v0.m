function y = dixon_func_4_v0(x)
	lambda = 0.0000000001;
	y = dixon_func_4(x) + (lambda/2)*norm(x)^2;
end