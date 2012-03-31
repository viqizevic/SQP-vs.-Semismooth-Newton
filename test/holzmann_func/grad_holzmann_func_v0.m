function g = grad_holzmann_func_v0(x)
	lambda = 0.00001;
	g = grad_holzmann_func(x) + lambda*x;
end