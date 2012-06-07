%problem_A_miele_hs52
%(Vgl. Problem 52 in \cite[S.~75]{hock})
%\min_{x\in\R^5}\ (4 x_1 - x_2)^2 + (x_2 + x_3 - 2)^2 + (x_4 - 1)^2 + (x_5 - 1)^2
%\nb x_1 + 3 x_2 & = 0 \\
%x_3 + x_4 - 2 x_5 & = 0 \\
%x_2 - x_5 & = 0 \\
%
function y = func_for_problem_A_miele_hs52(x)
	Q = 2*[16 -4 0 0 0; -4 2 1 0 0; 0 1 1 0 0; 0 0 0 1 0; 0 0 0 0 1];
	q = [0; -4; -4; -2; -2];
	c = 6;
	y = 0.5*x'*Q*x + q'*x + c;
end