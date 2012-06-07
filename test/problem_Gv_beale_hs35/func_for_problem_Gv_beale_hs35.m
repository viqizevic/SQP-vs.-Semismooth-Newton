%problem_Gv_beale_hs35
%(Vgl. Problem 35 in \cite[S.~58]{hock})
%\min_{x\in\R^3}\ 2 x_1^2 + 2 x_1 x_2 + 2 x_1 x_3 + 2 x_2^2 + x_3^2 - 8 x_1 - 6 x_2 - 4 x_3 + 9
%\nb x_1 + x_2 + 2 x_3 & \leq 3 \\
%0 & \leq x_i,\ i = 1,2,3 \\
%
function y = func_for_problem_Gv_beale_hs35(x)
	Q = 2*[2 1 1; 1 2 0; 1 0 1];
	q = [-8; -6; -4];
	c = 9;
	y = 0.5*x'*Q*x + q'*x + c;
end