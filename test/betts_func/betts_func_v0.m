function y = betts_func_v0(x)
	lambda = 0.01;
	y = betts_func(x) + (lambda/2)*norm(x)^2;
end