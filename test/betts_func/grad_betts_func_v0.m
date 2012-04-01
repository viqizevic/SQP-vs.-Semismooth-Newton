function g = grad_betts_func_v0(x)
	lambda = 0.001;
	g = grad_betts_func(x) + lambda*x;
end