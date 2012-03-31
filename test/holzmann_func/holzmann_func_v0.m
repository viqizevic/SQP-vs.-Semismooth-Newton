function y = holzmann_func_v0(x)
	lambda = 0.00001;
	y = holzmann_func(x) + (lambda/2)*norm(x)^2;
end