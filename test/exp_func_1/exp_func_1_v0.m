function y = exp_func_1_v0(x)
	lambda = 1;
	y = exp_func_1(x) + (lambda/2)*norm(x)^2;
end