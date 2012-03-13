function g = grad_dixon_func_2_v0(x)
	lambda = 0.0000000001;
	g = grad_dixon_func_2(x) + lambda*x;
end