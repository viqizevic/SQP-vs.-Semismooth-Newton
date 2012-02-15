function g = grad_bazaraa_shetty_func_v0(x)
	lambda = 0.000000001;
	g = grad_bazaraa_shetty_func(x) + lambda*x;
end