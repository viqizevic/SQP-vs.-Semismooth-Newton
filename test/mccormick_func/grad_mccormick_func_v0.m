function g = grad_mccormick_func_v0(x)
	lambda = 0.00001;
	g = grad_mccormick_func(x) + lambda*x;
end