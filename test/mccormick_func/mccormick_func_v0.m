function y = mccormick_func_v0(x)
	lambda = 0.00001;
	y = mccormick_func(x) + (lambda/2)*norm(x)^2;
end