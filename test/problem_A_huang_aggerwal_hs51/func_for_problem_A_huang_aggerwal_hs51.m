%problem_A_huang_aggerwal_hs51
%(Vgl. Problem 51 in \cite[S.~74]{hock})
%\min_{x\in\R^5}\ (x_1 - x_2)^2 + (x_2 + x_3 - 2)^2 + (x_4 - 1)^2 + (x_5 - 1)^2
%\nb x_1 + 3 x_2 & = 4 \\
%x_3 + x_4 - 2 x_5 & = 0 \\
%x_2 - x_5 & = 0 \\
%
function y = func_for_problem_A_huang_aggerwal_hs51(x)
	Q = 2*[1 -1 0 0 0; -1 2 1 0 0; 0 1 1 0 0; 0 0 0 1 0; 0 0 0 0 1];
	q = [0; -4; -4; -2; -2];
	c = 6;
	y = 0.5*x'*Q*x + q'*x + c;
end