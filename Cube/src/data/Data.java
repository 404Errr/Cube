package data;

public interface Data {
	int[][][] CUBE1 = {
		{
			{3,3,2},
			{3,1,2},
			{3,2,2},
		},
		{
			{3,4,2},
			{1,1,5},
			{1,5,5},
		},
		{
			{4,4,4},
			{4,5,4},
			{1,5,5},
		},
	};

	int[][][] CUBE3 = {
		{
			{1,1,1},
			{2,1,3},
			{2,2,2},
		},
		{
			{5,5,4},
			{6,6,3},
			{6,3,3},
		},
		{
			{5,4,4},
			{7,7,4},
			{6,7,7},
		},
	};
	
	int[][][] TRICKY2 = {
		{
			{2,5,5},
			{3,5,5},
			{3,3,3},	
		},
		{
			{2,5,4},
			{1,4,4},
			{1,4,3},	
		},
		{
			{2,5,4},
			{2,2,2},
			{1,1,1},	
		},
	};
	
	int[][][] TEST_START = {
			{
				{0,5,5},
				{0,5,5},
				{0,0,0},	
			},
			{
				{0,5,0},
				{0,0,0},
				{0,0,0},	
			},
			{
				{0,5,0},
				{0,0,0},
				{0,0,0},	
			},
	};
	
//
//	int[][][][] HARD = {
//			{
//				{
//					{1,1,1},
//					{0,0,0},
//					{0,0,0},
//				},
//				{
//					{1,0,0},
//					{1,0,0},
//					{1,0,0},
//				},
//			},
//			{
//				{
//					{2,0,0},
//					{2,2,2},
//				},
//				{
//					{2,0,0},
//					{0,0,0},
//				},
//			},
//			{
//				{
//					{3,0,0},
//					{3,3,3},
//				},
//				{
//					{3,0,0},
//					{0,0,0},
//				},
//			},
//			{
//				{
//					{0,4,4},
//					{4,4,0},
//				},
//				{
//					{0,0,0},
//					{4,0,0},
//				},
//			},
//			{
//				{
//					{0,0,5},
//					{0,5,5},
//					{0,0,0},
//				},
//				{
//					{0,0,0},
//					{0,5,0},
//					{5,5,0},
//				},
//			},
//	};

	int[][][][] PIECES1 = {
		{//brown
			{
				{0,1,0},
				{0,1,1},
			},
			{
				{1,1,0},
				{0,0,0},
			},
		},
		{//cyan
			{
				{0,0,2},
				{2,2,2},
			},
			{
				{0,0,0},
				{2,0,0},
			}
		},
		{//orange
			{
				{3,0,0},
				{3,3,3},
			},
			{
				{0,0,0},
				{3,0,0},
			},
		},
		{//red
			{
				{4,0,4},
				{4,4,4},
			},
			{
				{0,0,0},
				{0,4,0},
			},
		},
		{//white
			{
				{0,5},
				{5,5},
			},
			{
				{5,0},
				{5,5},
			},
		},

	};

//	255
//	254
//	224
//	---
//	255
//	115
//	344
//	---
//	311
//	314
//	334
}
