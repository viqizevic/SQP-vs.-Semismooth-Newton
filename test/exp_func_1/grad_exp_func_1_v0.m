function g = grad_exp_func_1_v0(x)
	lambda = 1;
	g = grad_exp_func_1(x) + lambda*x;
end