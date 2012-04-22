function H = hess_dixon_3_dim_func(x)
	H = [ 2+12*x(1)^2    -4*x(1)       0;
             -4*x(1)   2+12*x(2)^2  -4*x(2);
               0         -4*x(2)       4 ];
end