%problem_Gv_murtagh_sargent_hs76
%(Vgl. Problem 76 in \cite[S.~96]{hock})
%\min_{x\in\R^4}\ x_1^2 + 0.5 x_2^2 + x_3^2 + 0.5 x_4^2 - x_1 x_3 + x_3 x_4 - x_1 - 3 x_2 + x_3 - x_4
%\nb x_1 + 2 x_2 + x_3 + x_4 & \leq 5 \\
%3 x_1 + x_2 + 2 x_3 - x_4 & \leq 4 \\
%-x_2 - 4 x_3 & \leq -1.5 \\
%0 & \leq x_i,\ i = 1,\ldots,4 \\
%
function y = func_for_problem_Gv_murtagh_sargent_hs76(x)
	Q = 2*[1 0 -0.5 0; 0 0.5 0 0; -0.5 0 1 0.5; 0 0 0.5 0.5];
	q = [-1; -3; 1; -1];
	c = 0;
	y = 0.5*x'*Q*x + q'*x + c;
end