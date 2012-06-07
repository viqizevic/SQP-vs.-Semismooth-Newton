%problem_Gv_betts_hs21
%(Vgl. Problem 21 in \cite[S.~44]{hock})
%\min_{x\in\R^2}\ 0.01 x_1^2 + x_2^2 - 100
%\nb -10 x_1 + x_2 & \leq -10 \\
%2 \leq x_1 & \leq 50 \\
%-50 \leq x_2 & \leq 50 \\
%
function y = func_for_problem_Gv_betts_hs21(x)
	Q = 2*[0.01 0; 0 1];
	q = [0; 0];
	c = -100;
	y = 0.5*x'*Q*x + q'*x + c;
end