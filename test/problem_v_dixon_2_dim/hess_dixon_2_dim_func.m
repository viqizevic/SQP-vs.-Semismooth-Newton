function H = hess_dixon_2_dim_func(x)
	H = [ 2+12*x(1)^2-4*x(2)  -4*x(1);
               -4*x(1)           4   ];
end