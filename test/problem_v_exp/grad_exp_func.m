function g = grad_exp_func(x)
	g = 2*exp(norm(x)^2)*x;
end