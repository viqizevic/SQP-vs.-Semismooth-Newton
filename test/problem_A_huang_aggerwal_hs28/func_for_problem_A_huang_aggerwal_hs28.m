%problem_A_huang_aggerwal_hs28
%(Vgl. Problem 28 in \cite[S.~51]{hock})
%\min_{x\in\R^3}\ (x_1 + x_2)^2 + (x_2 + x_3)^2
%\nb x_1 + 2 x_2 + 3 x_3 & = 1 \\
%
function y = func_for_problem_A_huang_aggerwal_hs28(x)
	Q = 2*[1 1 0; 1 2 1; 0 1 1];
	q = [0; 0; 0];
	c = 0;
	y = 0.5*x'*Q*x + q'*x + c;
end