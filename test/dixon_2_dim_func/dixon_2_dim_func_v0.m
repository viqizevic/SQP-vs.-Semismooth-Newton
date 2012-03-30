function y = dixon_2_dim_func_v0(x)
	lambda = 0.00001;
	y = dixon_2_dim_func(x) + (lambda/2)*norm(x)^2;
end